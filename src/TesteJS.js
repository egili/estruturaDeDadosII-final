import { LightningElement, wire, track } from 'lwc';
import { getPicklistValues, getObjectInfo } from 'lightning/uiObjectInfoApi';
import minStock from '@salesforce/label/c.MinStockAvailable';
import enterValue from '@salesforce/label/c.EnterValue';
import getSalesConfigPicklists from '@salesforce/apex/PrecificacaoOppController.getSalesConfigPicklists';
import cleanFilter from '@salesforce/label/c.CleanFilter';
import filter from '@salesforce/label/c.Filter';

export default class OpportunityCatalogLeftMenu extends LightningElement {

    @track minStock = "";
    linePicklists;
    formatPicklists;
    
    @wire(getObjectInfo, { objectApiName: "Product2" })
    productMetadata;
    
    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.CommercialTypology__c" })
    typologyPicklist;

    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.Color__c" })
    colorPicklist;

    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.MaterialLookandfeel__c" })
    materialPicklist;
    
    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.Lifecycle__c" })
    lifecyclePicklist;
    
    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.Classification__c" })
    classificationPicklist;
    
    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.QuantityUnitOfMeasure" })
    measurePicklist;
    
    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.Border__c" })
    borderPicklist;
    
    @wire(getPicklistValues, { recordTypeId: "$productMetadata.data.defaultRecordTypeId", fieldApiName: "Product2.FinishingPrincipalSurface__c" })
    surfacePicklist;

    @wire(getSalesConfigPicklists, {recordtypeNames : ["Line", "NominalFormat"] })
    getSalesConfigPicklistsCallback({error, data}) {
        if (data) {
            if (data.Line) {
                this.linePicklists = data.Line.map((x) => { return { value : x.Name, label : x.Name }});
            }
            if (data.NominalFormat) {
                this.formatPicklists = data.NominalFormat.map((x) => { return { value : x.Name, label : x.Name }});;
            }
        }
    };

    customLabel = {
        cleanFilter,
        filter
    };

    onMinStockChanged(event) {
        this.minStock = event.target.value;
    }

    applyFilters(event) {

        let filter = {};

        let children = this.template.querySelectorAll('c-picklist-multi-select');

        children.forEach(child => {
            if (child.selected && child.selected.length > 0) {
                filter[child.apiName] = child.selected.map(x => x.value);
            }
        });

        if (this.minStock && parseInt(this.minStock) > 0) {
            filter.Balance__c = [this.minStock];
        }
        
        this.dispatchEvent(new CustomEvent('filterchanged', { detail : { leftFilter : filter } }));
    }

    clearFilter(event) {
        let children = this.template.querySelectorAll('c-picklist-multi-select');

        children.forEach(child => {
            child.removeAllItems();
        });

        this.minStock = "";

        this.applyFilters();
    }

    clickedPicklist(event) {
        let children = this.template.querySelectorAll('c-picklist-multi-select');

        children.forEach(child => {
            if (child.apiName != event.detail) {
                child.picklistOpen = false;
            }
        });
    }
    
    label = {
        minStock,
        enterValue
    };

    get loaded() {
        return this.productMetadata && this.productMetadata.data && this.productMetadata.data.fields;
    }
}