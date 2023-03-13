package com.astix.allanasosfa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.model.TblStoreListMaster;
import com.astix.allanasosfa.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreViewHolder> {
    Context context;
    List<TblStoreListMaster> storeListMasters, filteredStoreList;
    public StoreListAdapter(Context context, List<TblStoreListMaster> storeListMasters) {
        this.context=context;
        this.storeListMasters = storeListMasters;
        this.filteredStoreList = new ArrayList<>();
        this.filteredStoreList.addAll(storeListMasters);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.table_rowstoreselectionvalues,viewGroup,false);
        return new StoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder storeViewHolder, int i) {

        TblStoreListMaster tblStoreListMaster = filteredStoreList.get(i);
        storeViewHolder.tv_StoreName.setText(tblStoreListMaster.getStoreName());
if(CommonInfo.FlgDSRSO==1 || CommonInfo.FlgDSRSO==2 || CommonInfo.FlgDSRSO==3)
{
    storeViewHolder.tv_StoreName.setVisibility(View.GONE);
    storeViewHolder.ll_secondaryrow.setVisibility(View.GONE);
    storeViewHolder.tv_LastVisitDate.setVisibility(View.GONE);
    storeViewHolder.tv_LastOrderDate.setVisibility(View.GONE);
    storeViewHolder.rg1StoreName.setText(tblStoreListMaster.getStoreName());
    storeViewHolder.rg1StoreName.setVisibility(View.VISIBLE);
    storeViewHolder.rg1StoreName.setTag(tblStoreListMaster.getStoreID());
    if (tblStoreListMaster.getSstat().equals("3")|| tblStoreListMaster.getSstat().equals("5")|| tblStoreListMaster.getSstat().equals("6")) {
        if (tblStoreListMaster.getISNewStore()==1)//New Store
        {
            if (tblStoreListMaster.getStoreStatusVisitedOfNewStore()==1) {

                SpannableString spanString = new SpannableString(storeViewHolder.rg1StoreName.getText());
                spanString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.primaryDarkColor)), 0,  spanString.length(), 0);
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
                storeViewHolder.rg1StoreName.setText(spanString);
            } else {
					/*rb1.setTypeface(null, Typeface.BOLD);
					rb1.setTextColor(this.getResources().getColor(R.color.static_text_color));*/
                SpannableString spanString = new SpannableString(storeViewHolder.rg1StoreName.getText());
                spanString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.primary_color)), 0,  spanString.length(), 0);

                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
                storeViewHolder.rg1StoreName.setText(spanString);
            }
        } else {
            //rb1.setTypeface(null, Typeface.BOLD);
            storeViewHolder.rg1StoreName.setTextColor(context.getResources().getColor(R.color.primaryColorDark));


        }
    }
    else if ((tblStoreListMaster.getSstat().equals("1")))
    {
        storeViewHolder.rg1StoreName.setTextColor(context.getResources().getColor(R.color.red));
    }

    if(CommonInfo.FlgDSRSO==1 || CommonInfo.FlgDSRSO==2 || CommonInfo.FlgDSRSO==3) {
        if(tblStoreListMaster.getFlgRadioButtonSelected()==1)
        {
            storeViewHolder.rg1StoreName.setChecked(true);
        }
        else
        {
            storeViewHolder.rg1StoreName.setChecked(false);
        }
    }

}
else
{
    storeViewHolder.rg1StoreName.setVisibility(View.GONE);
    storeViewHolder.tv_StoreName.setVisibility(View.VISIBLE);
}



        storeViewHolder.tv_LastVisitDate.setText(tblStoreListMaster.getLastVisitDate());
        storeViewHolder.tv_NoOfBrands.setText(String.valueOf(tblStoreListMaster.getNoOfBrands()));
        storeViewHolder.tv_P3MValue.setText(String.valueOf(tblStoreListMaster.getP3MValue()));
        storeViewHolder.tv_MTDValue.setText(String.valueOf(tblStoreListMaster.getMTDValue()));
        storeViewHolder.tv_LastOrderDate.setText(tblStoreListMaster.getLastTransactionDate());

        if(CommonInfo.FlgDSRSO==1 || CommonInfo.FlgDSRSO==2 || CommonInfo.FlgDSRSO==2)
        {

        }
        else {

            storeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PurchaseOrderWebActivity.class);
                    intent.putExtra(PurchaseOrderWebActivity.REPORT_TYPE_KEY, 1);
                    intent.putExtra(AppUtils.StoreId, tblStoreListMaster.getStoreID());
                    context.startActivity(intent);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return filteredStoreList.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView tv_StoreName, tv_LastVisitDate, tv_NoOfBrands, tv_P3MValue, tv_MTDValue, tv_LastOrderDate;
        RadioButton rg1StoreName;
        LinearLayout ll_secondaryrow;


        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_StoreName = (TextView)itemView.findViewById(R.id.tv_StoreName);
            tv_LastVisitDate = (TextView)itemView.findViewById(R.id.tv_LastVisitDate);
            tv_NoOfBrands = (TextView)itemView.findViewById(R.id.tv_NoOfBrands);
            tv_P3MValue = (TextView)itemView.findViewById(R.id.tv_P3MValue);
            tv_MTDValue = (TextView)itemView.findViewById(R.id.tv_MTDValue);
            tv_LastOrderDate = (TextView)itemView.findViewById(R.id.tv_LastOrderDate);
            rg1StoreName = (RadioButton)itemView.findViewById(R.id.rg1StoreName);
            ll_secondaryrow=(LinearLayout)itemView.findViewById(R.id.ll_secondaryrow);

            rg1StoreName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton rb = (RadioButton) v;
                    if (rb.isChecked()) {
                        String selStoreID = v.getTag().toString();
                    /*    storeListMasters.forEach(wp->{ if (wp.getStoreID().equals(selStoreID)) { wp.setFlgRadioButtonSelected(1);
                        } else {
                            wp.setFlgRadioButtonSelected(0);
                        }}
                        );*/
                                new AsyncTask<Void,Void,Void>(){

                                    @Override
                                    protected Void doInBackground(Void... acd) {
                                        for (TblStoreListMaster wp : storeListMasters) {

                                            if (wp.getStoreID().equals(selStoreID)) {
                                                wp.setFlgRadioButtonSelected(1);
                                            } else {
                                                wp.setFlgRadioButtonSelected(0);
                                            }
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        super.onPostExecute(aVoid);
                                        notifyDataSetChanged();
                                        InterfaceRetrofitCallStoreSelectedBySO intrfc = (InterfaceRetrofitCallStoreSelectedBySO) context;

                                        intrfc.fnCallSelectedStoreBySO(selStoreID);

                                    }
                                }.execute();

                    }
                    else
                    {
                        for (TblStoreListMaster wp : storeListMasters) {

                                wp.setFlgRadioButtonSelected(0);

                        }
                        notifyDataSetChanged();
                    }


                }
            });

        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        filteredStoreList.clear();
        if (charText.length() == 0) {
            filteredStoreList.addAll(storeListMasters);
        } else {
            for (TblStoreListMaster wp : storeListMasters) {
                if (wp.getStoreName().toLowerCase().contains(charText)) {
                    if (filteredStoreList.contains(wp))
                    {

                    }
                    else
                    {filteredStoreList.add(wp);}

                }
            }
        }
        notifyDataSetChanged();

    }


}
