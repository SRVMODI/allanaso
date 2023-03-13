package com.astix.allanasosfa;

import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class ProductFilledDataModel {

    private LinkedHashMap<String, String> hmapProductTotalQty = new LinkedHashMap<String, String>();

    private LinkedHashMap<String, String> hmapPrdctProductOnlyOrderQty = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctOrderQty = new LinkedHashMap<String, String>();

    private LinkedHashMap<String, String> hmapPrdctOrderVal = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctTotalOrderVal = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctOnlyOrderVal = new LinkedHashMap<String, String>();


    private LinkedHashMap<String, String> hmapPrdctRateToApply = new LinkedHashMap<String, String>();
    private EditText lastEditText;
    private EditText focusLostEditText;

    public EditText getFocusLostEditText() {
        return focusLostEditText;
    }

    public void setFocusLostEditText(EditText focusLostEditText) {
        this.focusLostEditText = focusLostEditText;
    }


    //ProductRate
    public void setPrdctRateToApply(String prdctId, String prdctVal) {
        hmapPrdctRateToApply.put(prdctId, prdctVal);
    }

    public String getPrdctRateToApply(String prdctId) {
        String prdctAppliedRate = "";
        if ((hmapPrdctRateToApply != null) && (hmapPrdctRateToApply.containsKey(prdctId))) {
            prdctAppliedRate = hmapPrdctRateToApply.get(prdctId);
        }
        return prdctAppliedRate;
    }

    public void removePrdctRateToApply(String prdctId) {
        if ((hmapPrdctRateToApply != null) && (hmapPrdctRateToApply.containsKey(prdctId))) {
            hmapPrdctRateToApply.remove(prdctId);
        }
    }
    //hmapPrdctRateToApply

    //Product Quantity

    public void setPrdctQty(String prdctId, String prdctQty) {
        hmapPrdctOrderQty.put(prdctId, prdctQty);
    }

    public void setPrdctOnlyOrderQty(String prdctId, String prdctQty) {
        hmapPrdctProductOnlyOrderQty.put(prdctId, prdctQty);
    }

    public void setPrdctTotalQty(String prdctId, String prdctQty) {
        hmapProductTotalQty.put(prdctId, prdctQty);
    }


    public String getPrdctOrderQty(String prdctId) {
        String prdctQty = "";
        if ((hmapPrdctOrderQty != null) && (hmapPrdctOrderQty.containsKey(prdctId))) {
            prdctQty = hmapPrdctOrderQty.get(prdctId);
        }
        return prdctQty;
    }


    public String getPrdctOnlyOrderQty(String prdctId) {
        String prdctQty = "";
        if ((hmapPrdctProductOnlyOrderQty != null) && (hmapPrdctProductOnlyOrderQty.containsKey(prdctId))) {
            prdctQty = hmapPrdctProductOnlyOrderQty.get(prdctId);
        }
        return prdctQty;
    }

    public String getPrdctTotalQty(String prdctId) {
        String prdctQty = "";
        if ((hmapProductTotalQty != null) && (hmapProductTotalQty.containsKey(prdctId))) {
            prdctQty = hmapProductTotalQty.get(prdctId);
        }
        return prdctQty;
    }

    public void removePrdctQty(String prdctId) {
        if ((hmapPrdctOrderQty != null) && (hmapPrdctOrderQty.containsKey(prdctId))) {
            hmapPrdctOrderQty.remove(prdctId);
        }
    }

    public void removePrdctTotalQty(String prdctId) {
        if ((hmapProductTotalQty != null) && (hmapProductTotalQty.containsKey(prdctId))) {
            hmapProductTotalQty.remove(prdctId);
        }
    }

    public void removeProductOnlyOrderQty(String prdctId) {
        if ((hmapPrdctProductOnlyOrderQty != null) && (hmapPrdctProductOnlyOrderQty.containsKey(prdctId))) {
            hmapPrdctProductOnlyOrderQty.remove(prdctId);
        }
    }

    //ProductValue
    public void setPrdctVal(String prdctId, String prdctVal) {
        hmapPrdctOrderVal.put(prdctId, prdctVal);
    }

    public void setPrdctTotalProductVal(String prdctId, String prdctVal) {
        hmapPrdctTotalOrderVal.put(prdctId, prdctVal);
    }

    //hmapPrdctTotalOrderVal
    public void setPrdctOnlyOrderVal(String prdctId, String prdctVal) {
        hmapPrdctOnlyOrderVal.put(prdctId, prdctVal);
    }


    public Double getPrdctTotOrderVal(String prdctId) {
        Double prdctVal = 0.0;
        if ((hmapPrdctTotalOrderVal != null) && (hmapPrdctTotalOrderVal.containsKey(prdctId))) {
            prdctVal = Double.parseDouble(hmapPrdctTotalOrderVal.get(prdctId));
            prdctVal = Double.parseDouble(new DecimalFormat("##.##").format(prdctVal));
        }
        return prdctVal;
    }


    public Double getPrdctOrderVal(String prdctId) {
        Double prdctVal = 0.0;
        if ((hmapPrdctOrderVal != null) && (hmapPrdctOrderVal.containsKey(prdctId))) {
            prdctVal = Double.parseDouble(hmapPrdctOrderVal.get(prdctId));
            prdctVal = Double.parseDouble(new DecimalFormat("##.##").format(prdctVal));
        }
        return prdctVal;
    }

    public Double getPrdctOnlyOrderVal(String prdctId) {
        Double prdctVal = 0.0;
        if ((hmapPrdctOnlyOrderVal != null) && (hmapPrdctOnlyOrderVal.containsKey(prdctId))) {
            prdctVal = Double.parseDouble(hmapPrdctOnlyOrderVal.get(prdctId));
            prdctVal = Double.parseDouble(new DecimalFormat("##.##").format(prdctVal));
        }
        return prdctVal;
    }

    public void removePrdctVal(String prdctId) {
        if ((hmapPrdctOrderVal != null) && (hmapPrdctOrderVal.containsKey(prdctId))) {
            hmapPrdctOrderVal.remove(prdctId);
        }
    }

    public void removePrdctOnlyOrderVal(String prdctId) {
        if ((hmapPrdctOnlyOrderVal != null) && (hmapPrdctOnlyOrderVal.containsKey(prdctId))) {
            hmapPrdctOnlyOrderVal.remove(prdctId);
        }
    }

    public void setLastEditText(EditText lastEditText) {
        this.lastEditText = lastEditText;
    }

    public EditText getLastEditText() {
        return lastEditText;
    }

    public int gethmapPrdctOrderQtySize() {
        int totalSize = 0;
        if (hmapPrdctOrderQty != null) {
            totalSize = hmapPrdctOrderQty.size();
        }
        return totalSize;
    }

    public LinkedHashMap<String, String> getHmapPrdctOrderQty() {
        return hmapPrdctOrderQty;
    }

    public LinkedHashMap<String, String> getHmapProductTotalQty() {
        return hmapProductTotalQty;
    }

    public LinkedHashMap<String, String> getHmapProductOnlyOrderQty() {
        return hmapPrdctProductOnlyOrderQty;
    }

    public void fnRequestToNotifyAllChanges(LinkedHashMap<String, String> hmapPrdctRateToApplyOnEntryForm) {
        hmapPrdctRateToApply.clear();
        hmapPrdctRateToApply.putAll(hmapPrdctRateToApplyOnEntryForm);


        //ProductFilledDataModel.class.notifyAll();
    }
}
