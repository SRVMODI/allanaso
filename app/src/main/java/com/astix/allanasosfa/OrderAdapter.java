package com.astix.allanasosfa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.sancussosfa.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    LinkedHashMap<String, String> hmapFilterProductList ;
    HashMap<String, String> hmapProductStandardRate;
    HashMap<String, String> hmapProductIdStock;
    HashMap<String, String> hmapProductMRP;
    String[] listProduct;
    private LayoutInflater inflater;
    ProductFilledDataModel prdctModelArrayList;
    focusLostCalled interfacefocusLostCalled;
    HashMap<String, String> hmapProductIdLastStock;
    LinkedHashMap<String,String> hampGetLastProductExecution;
    HashMap<String, Integer> hmapDistPrdctStockCount;
    HashMap<String, Integer> hmapVanPrdctStockCount;
    HashMap<String, String> hmapProductIdLastOrder=new HashMap<String, String>();
    Context context;

    int flgEntryOrReview;

    public OrderAdapter(Context context,String[] listProduct,LinkedHashMap<String, String> hmapFilterProductList,HashMap<String, String> hmapProductStandardRate,HashMap<String, String> hmapProductMRP,HashMap<String, String> hmapProductIdStock,HashMap<String, String> hmapProductIdLastStock, LinkedHashMap<String,String> hampGetLastProductExecution,HashMap<String, Integer> hmapDistPrdctStockCount,HashMap<String, Integer> hmapVanPrdctStockCount,ProductFilledDataModel prdctModelArrayList, int flgEntryOrReview,HashMap<String, String> hmapProductIdLastOrder)
    {
        interfacefocusLostCalled= (focusLostCalled) context;
        inflater = LayoutInflater.from(context);
        this.hmapFilterProductList=hmapFilterProductList;
        this.prdctModelArrayList=prdctModelArrayList;
        this.hmapProductStandardRate=hmapProductStandardRate;
        this.hmapProductMRP=hmapProductMRP;
        this.hmapProductIdStock=hmapProductIdStock;
        this.listProduct=listProduct;

        this.hmapProductIdLastStock=hmapProductIdLastStock;
        this.hampGetLastProductExecution=hampGetLastProductExecution;
        this.hmapDistPrdctStockCount=hmapDistPrdctStockCount;
        this.hmapVanPrdctStockCount=hmapVanPrdctStockCount;
        this.flgEntryOrReview=flgEntryOrReview;
        this.hmapProductIdLastOrder=hmapProductIdLastOrder;
        this.context = context;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_card, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if ((position + 1) % 2 == 0) {
            holder.ll_PrdctPage.setBackgroundResource(R.drawable.card_background);
        } else {
            holder.ll_PrdctPage.setBackgroundResource(R.drawable.card_background_white);
        }
        String prductId = listProduct[position].split(Pattern.quote("^"))[0];
        String prductName = listProduct[position].split(Pattern.quote("^"))[1];
        holder.tvProdctName.setText(prductName);
        if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp")==1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")==0))
        {
            if (hmapDistPrdctStockCount != null && hmapDistPrdctStockCount.size() > 0) {
                if (hmapDistPrdctStockCount.containsKey(prductId)) {

                    holder.tvProdctName.setText(prductName+"( Avl : " + hmapDistPrdctStockCount.get(prductId)+")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                } else {
                    holder.tvProdctName.setText(prductName);
                }
            } else {
                holder.tvProdctName.setText(prductName);
            }
        }
        else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp")==0) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")==1))
        {
            if (hmapVanPrdctStockCount != null && hmapVanPrdctStockCount.size() > 0) {
                if (hmapVanPrdctStockCount.containsKey(prductId)) {

                    holder.tvProdctName.setText(prductName+"( Avl : " + hmapVanPrdctStockCount.get(prductId)+")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                } else {
                    holder.tvProdctName.setText(prductName);
                }
            } else {
                holder.tvProdctName.setText(prductName);
            }
        }
        else if((CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp")==1) && (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp")==1))
        {
            if ((hmapDistPrdctStockCount != null && hmapDistPrdctStockCount.size() > 0) ) {
                if (hmapDistPrdctStockCount.containsKey(prductId)) {

                    if((hmapVanPrdctStockCount != null && hmapVanPrdctStockCount.size() > 0))
                    {
                        if (hmapVanPrdctStockCount.containsKey(prductId))
                        {
                            holder.tvProdctName.setText(prductName+"(D.Avl : " + hmapDistPrdctStockCount.get(prductId)+")"+"(V.Avl : " + hmapVanPrdctStockCount.get(prductId)+")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                        }
                        else
                        {
                            holder.tvProdctName.setText(prductName+"(D.Avl : " + hmapDistPrdctStockCount.get(prductId)+")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                        }
                    }
                    else
                    {
                        holder.tvProdctName.setText(prductName+"(D.Avl : " + hmapDistPrdctStockCount.get(prductId)+")");//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
                    }

                }
                else {
                    holder.tvProdctName.setText(prductName);
                }
            } else if ((hmapVanPrdctStockCount != null && hmapVanPrdctStockCount.size() > 0) ) {
                if (hmapVanPrdctStockCount.containsKey(prductId))
                {
                    holder.tvProdctName.setText(prductName+"(V.Avl : " + hmapVanPrdctStockCount.get(prductId)+")");
                }
                else {
                    holder.tvProdctName.setText(prductName);
                }
            }

            else {
                holder.tvProdctName.setText(prductName);
            }
        }
        else
        {
            holder.tvProdctName.setText(prductName);
        }

        holder.txtVwRate.setText(hmapProductStandardRate.get(prductId));
        holder.et_ProductMRP.setText(hmapProductMRP.get(prductId));
        holder.et_OrderQty.setTag(prductId + "_etOrderQty");
        holder.et_OrderQty.setText(prdctModelArrayList.getPrdctOrderQty(prductId));
        holder.tv_Orderval.setTag(prductId + "_tvOrderval");
        holder.tv_Orderval.setText("" + prdctModelArrayList.getPrdctOrderVal(prductId));
        if ((hmapProductIdLastOrder != null) && (hmapProductIdLastOrder.containsKey(prductId))) {
            holder.et_LstOrder.setText(hmapProductIdLastOrder.get(prductId));
        } else
        {
            holder.et_LstOrder.setText("0");
        }
        if ((hmapProductIdStock != null) && (hmapProductIdStock.containsKey(prductId))) {
            holder.et_Stock.setText(hmapProductIdStock.get(prductId));
        } else
        {
            holder.et_Stock.setText("0");
        }
        int LastStockQty=0;
        int LastExecutionQty=0;
        int CurrentStockQty=0;
        if(hmapProductIdLastStock.containsKey(prductId))
        {
            LastStockQty=Integer.parseInt(hmapProductIdLastStock.get(prductId));
            holder.et_LstStock.setText(""+LastStockQty);
        }
        if(hampGetLastProductExecution.containsKey(prductId))
        {
            LastExecutionQty=Integer.parseInt(hampGetLastProductExecution.get(prductId));
        }
        if(hmapProductIdStock.containsKey(prductId))
        {
            CurrentStockQty=Integer.parseInt(hmapProductIdStock.get(prductId));
        }
      /*  int PStoreSale=((LastStockQty+LastExecutionQty)-CurrentStockQty);
        if(PStoreSale>0)
        {
            holder.et_StoreSale.setText(""+PStoreSale);
        }
       else
        {
            holder.et_StoreSale.setText("0");
        }*/
    }

    @Override
    public int getItemCount() {
        return listProduct.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvProdctName;
        public TextView txtVwRate;
        public EditText et_ProductMRP;
        public View layout;
        public EditText et_OrderQty,et_Stock,et_StoreSale,et_LstStock,et_LstOrder;
        public TextView tv_Orderval;
        public LinearLayout ll_PrdctPage;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            tvProdctName = (TextView) itemView.findViewById(R.id.tvProdctName);
            txtVwRate = (TextView) itemView.findViewById(R.id.txtVwRate);
            et_ProductMRP = (EditText) itemView.findViewById(R.id.et_ProductMRP);
            et_OrderQty = (EditText) itemView.findViewById(R.id.et_OrderQty);
            tv_Orderval = (TextView) itemView.findViewById(R.id.tv_Orderval);
            ll_PrdctPage= (LinearLayout) itemView.findViewById(R.id.ll_PrdctPage);
            et_Stock= (EditText) itemView.findViewById(R.id.et_Stock);
            // et_StoreSale= (EditText) itemView.findViewById(R.id.et_StoreSale);
            et_LstStock=(EditText) itemView.findViewById(R.id.et_LstStock);
            et_LstOrder=(EditText) itemView.findViewById(R.id.et_LstOrder);
            et_OrderQty.addTextChangedListener(new TextChangedListener(et_OrderQty,tv_Orderval));
            et_OrderQty.setOnFocusChangeListener(new FocusChangeList());
            et_OrderQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager im = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(et_OrderQty.getWindowToken(),0);
                }
            });

            if(flgEntryOrReview==0)
            {
                et_OrderQty.setEnabled(true);
            }
            else
            {
                et_OrderQty.setEnabled(false);
            }


        }
    }

    class TextChangedListener implements TextWatcher
    {
        EditText ediText;
        TextView txtOrderVal;
        public TextChangedListener(EditText ediText,TextView txtOrderVal)
        {
            this.ediText=ediText;
            this.txtOrderVal=txtOrderVal;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String prdctId=ediText.getTag().toString().split(Pattern.quote("_"))[0];
            if(!TextUtils.isEmpty(ediText.getText().toString().trim()))
            {

                String prdctOrderQty=ediText.getText().toString().trim();
                prdctModelArrayList.setPrdctQty(prdctId,prdctOrderQty);
                Double StandardRate=Double.parseDouble(hmapProductStandardRate.get(prdctId));



                Double OrderValPrdQtyBasis = StandardRate * Double.parseDouble(prdctOrderQty);
                prdctModelArrayList.setPrdctVal(prdctId,""+OrderValPrdQtyBasis);
                Double OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasis));
                txtOrderVal.setText(""+OrderValPrdQtyBasisToDisplay);
            }
            else
            {
                prdctModelArrayList.removePrdctQty(prdctId);
                prdctModelArrayList.removePrdctVal(prdctId);
                txtOrderVal.setText("0");
            }

        }
    }

    class FocusChangeList implements View.OnFocusChangeListener
    {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            EditText editText= (EditText) v;

            if(!hasFocus)
            {
                if(editText.getId()==R.id.et_OrderQty)
                {

                    prdctModelArrayList.setFocusLostEditText(editText);
                    interfacefocusLostCalled.fcsLstCld(hasFocus,editText);

                }
            }
            else
            {
                if(editText.getId()==R.id.et_OrderQty)
                {
                    prdctModelArrayList.setLastEditText(editText);
                    interfacefocusLostCalled.fcsLstCld(hasFocus,editText);
                }

            }
        }
    }
}
