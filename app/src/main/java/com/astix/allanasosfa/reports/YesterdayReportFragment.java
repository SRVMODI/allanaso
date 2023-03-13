package com.astix.allanasosfa.reports;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.model.TblRptDistribution;
import com.astix.allanasosfa.model.TblRptManDays;
import com.astix.allanasosfa.model.TblRptPrimaryVol;
import com.astix.allanasosfa.model.TblRptSecVol;
import com.astix.allanasosfa.utils.AppUtils;
import com.astix.allanasosfa.utils.IntentConstants;
import com.astix.allanasosfa.utils.PreferenceManager;
import com.astix.allanasosfa.utils.SPConstants;
import com.astix.allanasosfa.R;
import com.astix.allanasosfa.customwidgets.ViewGenerator;
import com.astix.allanasosfa.database.AppDataSource;
import com.astix.allanasosfa.model.TblRptDistribution;
import com.astix.allanasosfa.model.TblRptManDays;
import com.astix.allanasosfa.model.TblRptPrimaryVol;
import com.astix.allanasosfa.model.TblRptSecVol;
import com.astix.allanasosfa.utils.AppUtils;
import com.astix.allanasosfa.utils.IntentConstants;
import com.astix.allanasosfa.utils.PreferenceManager;
import com.astix.allanasosfa.utils.SPConstants;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class YesterdayReportFragment extends Fragment {


    @BindView(R.id.tvGreeting)
    TextView tvGreeting;
    @BindView(R.id.ll_Parent)
    LinearLayout ll_Parent;
    @BindView(R.id.tvPersonName)
    TextView tvPersonName;
    @BindView(R.id.tvDescr)
    TextView tvDescr;
    @BindView(R.id.bt_Next)
    Button btNext;
    ArrayList<TblRptDistribution> mTblDistributionArrayList;
    ArrayList<TblRptSecVol> mTblRptSecVolArrayList;
    ArrayList<TblRptPrimaryVol> mTblRptPrimaryVolArrayList;
    ArrayList<TblRptManDays> mTblRptManDayArrayList;
    private View view;
    private Activity mActitiy;
    private PreferenceManager mPreferenceManager;
    private AppDataSource mDataSource;

    public YesterdayReportFragment() {
        // Required empty public constructor
    }

    public static YesterdayReportFragment newInstance(Bundle args) {

        YesterdayReportFragment fragment = new YesterdayReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) mActitiy.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            if (view.getParent() != null)

                ((ViewGroup) view.getParent()).removeView(view);
            ll_Parent.removeAllViews();
            GetData getData = new GetData();
            AppUtils.executeAsyncTask(getData);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_yesterday_report, container, false);
        ButterKnife.bind(this, view);
        mActitiy = getActivity();


        new AppUtils().callParentMethod(mActitiy);
        mDataSource = new AppDataSource(mActitiy);
        mPreferenceManager = PreferenceManager.getInstance(mActitiy);

        tvGreeting.setText(AppUtils.getGreetingFortheDay() + ", ");
        tvPersonName.setText(mPreferenceManager.getStringValue(AppUtils.PERSON_NAME, ""));

        // String areaName = mDataSource.getSalesArea();
        String yesterdayDate = mPreferenceManager.getStringValue(SPConstants.YESTERDAY_DATE, "");

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescr.setText(Html.fromHtml("Here is a brief summary of the performance of <b>" + areaName + "</b> on <b>" + yesterdayDate + "</b>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescr.setText(Html.fromHtml("Here is a brief summary of the performance of <b>" + areaName + "</b> on <b>" + yesterdayDate + "</b>"));
        }*/

        if (getArguments() != null) {
            int flgShowNextButton = getArguments().getInt(IntentConstants.flgShowNextButton);
            if (flgShowNextButton == 0)
                btNext.setVisibility(View.GONE);
            else
                btNext.setVisibility(View.VISIBLE);
        }

        GetData getData = new GetData();
        AppUtils.executeAsyncTask(getData);
        return view;
    }

    private void init() {


        /***** Distributor Data Starts ********************/
        LayoutInflater inflater = (LayoutInflater) mActitiy.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_distributionheader, null);

        LinearLayout ll_distributorParentData = ViewGenerator.createVerticalLayout(mActitiy, "distribution");
        ll_distributorParentData.setTag("ll_");
        final LinearLayout ll_distributorChildData = ViewGenerator.createVerticalLayout(mActitiy, "distributionChild");
        ll_distributorChildData.setTag("1");
        for (int i = 0; i < mTblDistributionArrayList.size(); i++) {
            TblRptDistribution tblRptDistribution = mTblDistributionArrayList.get(i);
            View distributionChildView = null;
            if (tblRptDistribution.getFlgLevel() == 1)
                distributionChildView = inflater.inflate(R.layout.layout_distributionitem, null);
            else {
                distributionChildView = inflater.inflate(R.layout.layout_distributionitem2, null);

            }
            TextView etDescription = distributionChildView.findViewById(R.id.et_descr);
            EditText et_tgt = distributionChildView.findViewById(R.id.et_tgt);
            TextView et_tilldate = distributionChildView.findViewById(R.id.et_tilldate);
            EditText et_mtd = distributionChildView.findViewById(R.id.et_mtd);
            EditText et_rr_rqd = distributionChildView.findViewById(R.id.et_rr_rqd);

            etDescription.setText(tblRptDistribution.getDescription());
            et_tgt.setText(tblRptDistribution.getYTDTgt());
            et_tilldate.setText("" + tblRptDistribution.getYTDTillDate());
            et_mtd.setText("" + tblRptDistribution.getMTD());
            et_rr_rqd.setText("" + tblRptDistribution.getNewYesterDay());
            final ImageButton ibExpandCollapse = distributionChildView.findViewById(R.id.ibExpand);
            ibExpandCollapse.setTag("1_1");
            if (tblRptDistribution.getFlgLevel() == 1 && tblRptDistribution.getFlgCollapse() == 1)
                ibExpandCollapse.setVisibility(View.VISIBLE);
            else
                ibExpandCollapse.setVisibility(View.INVISIBLE);

            if (tblRptDistribution.getFlgLevel() == 1)
                ll_distributorParentData.addView(distributionChildView);
            else
                ll_distributorChildData.addView(distributionChildView);

            ibExpandCollapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String clickTag=v.getTag().toString().split(Pattern.quote("_"))[0];
                    LinearLayout linearLayoutdr = ll_Parent.findViewWithTag(clickTag);
                    if (linearLayoutdr.getVisibility() == View.VISIBLE) {
                        linearLayoutdr.setVisibility(View.GONE);
                        ibExpandCollapse.setImageResource(R.drawable.baseline_expand_more_black_18dp);
                    } else {
                        linearLayoutdr.setVisibility(View.VISIBLE);
                        ibExpandCollapse.setImageResource(R.drawable.baseline_expand_less_black_18dp);
                    }
                }
            });
          //  LinearLayout linearLayoutParent = ll_Parent.findViewWithTag(101);
            ll_distributorParentData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayoutFor1 = ll_Parent.findViewWithTag("1");
                    ImageButton ibExpandCollapse1 =(ImageButton) ll_Parent.findViewWithTag("1_1");
                    if (linearLayoutFor1.getVisibility() == View.VISIBLE) {
                        linearLayoutFor1.setVisibility(View.GONE);
                        ibExpandCollapse1.setImageResource(R.drawable.baseline_expand_more_black_18dp);
                    } else {
                        linearLayoutFor1.setVisibility(View.VISIBLE);
                        ibExpandCollapse1.setImageResource(R.drawable.baseline_expand_less_black_18dp);
                    }
                }
            });
        }

        ll_Parent.addView(view);
        ll_Parent.addView(ll_distributorParentData);
        ll_Parent.addView(ll_distributorChildData);
        ll_distributorChildData.setVisibility(View.GONE);

        /***** Distributor Data Ends ********************/


        /***** Secondary Volume Data Starts ********************/

        ll_Parent.addView(ViewGenerator.createMarginView(mActitiy));
        View secondaryVolumeView = inflater.inflate(R.layout.layout_secondaryvolumeheader, null);

        LinearLayout ll_secondaryVolumeParent = ViewGenerator.createVerticalLayout(mActitiy, "secondaryvolumeparent");
        final LinearLayout ll_secondaryVolumeChild = ViewGenerator.createVerticalLayout(mActitiy, "secondaryvolumechild");
        ll_secondaryVolumeChild.setTag("2");

        for (int i = 0; i < mTblRptSecVolArrayList.size(); i++) {
            TblRptSecVol tblRptSecVol = mTblRptSecVolArrayList.get(i);

            View distributionChildView;
            if (tblRptSecVol.getFlgLevel() == 1)
                distributionChildView = inflater.inflate(R.layout.layout_distributionitem, null);
            else
                distributionChildView = inflater.inflate(R.layout.layout_distributionitem2, null);


            final ImageButton ibExpandCollapse = distributionChildView.findViewById(R.id.ibExpand);
            ibExpandCollapse.setTag("2_2");
            if (tblRptSecVol.getFlgLevel() == 1 && tblRptSecVol.getFlgCollapse() == 1)
                ibExpandCollapse.setVisibility(View.VISIBLE);
            else
                ibExpandCollapse.setVisibility(View.INVISIBLE);

            TextView etDescription = distributionChildView.findViewById(R.id.et_descr);
            EditText et_tgt = distributionChildView.findViewById(R.id.et_tgt);
            TextView et_tilldate = distributionChildView.findViewById(R.id.et_tilldate);
            EditText et_mtd = distributionChildView.findViewById(R.id.et_mtd);
            EditText et_rr_rqd = distributionChildView.findViewById(R.id.et_rr_rqd);

            etDescription.setText(tblRptSecVol.getDescription());
            et_tgt.setText(tblRptSecVol.getMTDTgt());
            et_tilldate.setText("" + tblRptSecVol.getMTDTillDate());
            et_mtd.setText("" + tblRptSecVol.getYesterday());
            et_rr_rqd.setText("" + tblRptSecVol.getRRRequired());


            if (tblRptSecVol.getFlgLevel() == 1)
                ll_secondaryVolumeParent.addView(distributionChildView);
            else
                ll_secondaryVolumeChild.addView(distributionChildView);

            ibExpandCollapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String clickTag=v.getTag().toString().split(Pattern.quote("_"))[0];
                    LinearLayout linearLayoutdr = ll_Parent.findViewWithTag(clickTag);
                    if (linearLayoutdr.getVisibility() == View.VISIBLE) {
                        linearLayoutdr.setVisibility(View.GONE);
                        ibExpandCollapse.setImageResource(R.drawable.baseline_expand_more_black_18dp);
                    } else {
                        linearLayoutdr.setVisibility(View.VISIBLE);
                        ibExpandCollapse.setImageResource(R.drawable.baseline_expand_less_black_18dp);
                    }
                }
            });

            ll_secondaryVolumeParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayout = ll_Parent.findViewWithTag("2");
                    ImageButton ibExpandCollapse2 =(ImageButton) ll_Parent.findViewWithTag("2_2");
                    if (linearLayout.getVisibility() == View.VISIBLE) {
                        linearLayout.setVisibility(View.GONE);
                        ibExpandCollapse2.setImageResource(R.drawable.baseline_expand_more_black_18dp);
                    } else {
                        linearLayout.setVisibility(View.VISIBLE);
                        ibExpandCollapse2.setImageResource(R.drawable.baseline_expand_less_black_18dp);
                    }
                }
            });
        }

        ll_Parent.addView(secondaryVolumeView);
        ll_Parent.addView(ll_secondaryVolumeParent);
        ll_Parent.addView(ll_secondaryVolumeChild);
        ll_secondaryVolumeChild.setVisibility(View.GONE);


        /***** Secondary Volume Data Ends ********************/

        /***** ManDays Data Starts ********************/
        ll_Parent.addView(ViewGenerator.createMarginView(mActitiy));
        View manDaysView = inflater.inflate(R.layout.layout_mandaysheader, null);

        LinearLayout ll_manDays = ViewGenerator.createVerticalLayout(mActitiy, "mandays");
        for (int i = 0; i < mTblRptManDayArrayList.size(); i++) {
            View manworkChildView = inflater.inflate(R.layout.layout_dayworkitem, null);

            TblRptManDays tblRptManDays = mTblRptManDayArrayList.get(i);

            TextView etDescription = manworkChildView.findViewById(R.id.et_descr);
            EditText et_tgt = manworkChildView.findViewById(R.id.et_tgt);
            TextView et_tilldate = manworkChildView.findViewById(R.id.et_tilldate);
            EditText et_mtd = manworkChildView.findViewById(R.id.et_mtd);

            etDescription.setText(tblRptManDays.getDescription());
            et_tgt.setText("" + tblRptManDays.getPlanned());
            et_tilldate.setText("" + tblRptManDays.getInFieldYesterday());
            et_mtd.setText("" + tblRptManDays.getInFieldMTD());


            ll_manDays.addView(manworkChildView);
        }

        ll_Parent.addView(manDaysView);
        ll_Parent.addView(ll_manDays);

        /***** Secondary Volume Data Ends ********************/

    }

    @OnClick(R.id.bt_Next)
    public void onNextButtonClick() {
        ((ReportsActivity) mActitiy).loadFragment(null, ReportsActivity.DAY_PLAN_REPORT);
    }

    private class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            mTblDistributionArrayList = mDataSource.getRptDistributions();
            mTblRptSecVolArrayList = mDataSource.getRptSecondaryVols();
            mTblRptManDayArrayList = mDataSource.getManDays();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            init();
        }
    }


}
