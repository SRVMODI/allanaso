package com.astix.allanasosfa;

import android.widget.EditText;

import java.util.LinkedHashMap;

public class StockFilledDataModel {

   private LinkedHashMap<String,String> hmapPrdctStock=new LinkedHashMap<String,String>();

   //Product Quantity
   private EditText lastEditText;
    private EditText focusLostEditText;

    public EditText getFocusLostEditText() {
        return focusLostEditText;
    }

    public void setFocusLostEditText(EditText focusLostEditText) {
        this.focusLostEditText = focusLostEditText;
    }
    public void setPrdctStock(String prdctId,String prdctQty)
    {
        hmapPrdctStock.put(prdctId,prdctQty);
    }
    public String getPrdctStock(String prdctId)
    {
        String prdctStk="";
        if((hmapPrdctStock!=null) && (hmapPrdctStock.containsKey(prdctId)))
        {
            prdctStk=hmapPrdctStock.get(prdctId);
        }
        return prdctStk;
    }
    public void removePrdctStk(String prdctId)
    {
        if((hmapPrdctStock!=null) && (hmapPrdctStock.containsKey(prdctId)))
        {
            hmapPrdctStock.remove(prdctId);
        }
    }

    public int gethmapPrdctOrderQtySize()
    {
        int totalSize=0;
        if(hmapPrdctStock!=null)
        {
            totalSize=hmapPrdctStock.size();
        }
        return totalSize;
    }

    public LinkedHashMap<String, String> getHmapPrdctStock() {
        return hmapPrdctStock;
    }
}