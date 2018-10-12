package xyxgame.dhd.frament.Three;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.media.effect.Effect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import xyxgame.dhd.MainActivity;
import xyxgame.dhd.R;
import xyxgame.dhd.SQL.SQLBuilder;
import xyxgame.dhd.SQLSave.SQLSaveBuilder;
import xyxgame.dhd.Save.Find;
import xyxgame.dhd.frament.First.First;
import xyxgame.dhd.frament.NotifyDate;

public class Three extends Fragment {


    private NotifyDate notifyDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        notifyDate = (NotifyDate) getActivity();

        final View view = View.inflate(getContext(), R.layout.fragment_three, null);

        //*****************第一按钮事件*******************
        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view1 = View.inflate(getContext(), R.layout.add, null);
                final Spinner spinner = (Spinner) view1.findViewById(R.id.spinner);
                //将可选内容与ArrayAdapter连接起来
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, First.top);
                //设置下拉列表的风格
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //将adapter 添加到spinner中
                spinner.setAdapter(adapter);


                final Dialog lDialog = new Dialog(getContext(),
                        android.R.style.Theme_Translucent_NoTitleBar);
                lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                lDialog.setContentView(view1);


                ((Button) lDialog.findViewById(R.id.canel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lDialog.dismiss();
                    }
                });
                ((Button) lDialog.findViewById(R.id.ok)).setText("确定");
                ((Button) lDialog.findViewById(R.id.ok))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int i = spinner.getSelectedItemPosition();


                                if (!TextUtils.isEmpty(((EditText) lDialog.findViewById(R.id.e1)).getText().toString().trim()) && !TextUtils.isEmpty(((EditText) lDialog.findViewById(R.id.e2)).getText().toString().trim())) {
                                    String e1 = (((EditText) lDialog.findViewById(R.id.e1)).getText().toString());
                                    double e2 = Double.parseDouble(((EditText) lDialog.findViewById(R.id.e2)).getText().toString());

                                    int j = SQLBuilder.getInstance(getActivity()).insertAPI(i, e1, e2);//添加数据
                                    if (j == -1) {
                                        Toast.makeText(getContext(), "数据添加失败", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "数据添加成功", Toast.LENGTH_LONG).show();
                                        notifyDate.Notify();
                                        // write your code to do things after users clicks OK
                                        lDialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "输入不能为空", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                lDialog.show();
            }


        });


        //***************************第二button监听
        view.findViewById(R.id.Init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLBuilder.getInstance(getActivity()).InitDataTransaction(notifyDate);


            }
        });

        //*******************第三按钮删除监听****************
        view.findViewById(R.id.delet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view1 = View.inflate(getContext(), R.layout.add, null);
                final Dialog lDialog = new Dialog(getContext(),
                        android.R.style.Theme_Translucent_NoTitleBar);
                lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                lDialog.setContentView(view1);
                ((TextView) view1.findViewById(R.id.text)).setVisibility(View.GONE);
                ((Spinner) view1.findViewById(R.id.spinner)).setVisibility(View.GONE);
                 ((EditText) lDialog.findViewById(R.id.e2)).setVisibility(View.GONE);
                ((Button) lDialog.findViewById(R.id.ok)).setText("确定");
                ((Button) lDialog.findViewById(R.id.ok))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (!TextUtils.isEmpty(((EditText) lDialog.findViewById(R.id.e1)).getText().toString().trim()) ) {
                                    String e1 = (((EditText) lDialog.findViewById(R.id.e1)).getText().toString());

                                    int j = SQLBuilder.getInstance(getActivity()).deleteAPI(e1);//添加数据
                                    if (j ==0) {
                                        Toast.makeText(getContext(), "数据删除失败", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "数据删除成功", Toast.LENGTH_LONG).show();
                                        notifyDate.Notify();
                                        // write your code to do things after users clicks OK
                                        lDialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "输入不能为空", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                ((Button) lDialog.findViewById(R.id.canel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lDialog.dismiss();
                    }
                });
                lDialog.show();

            }
        });




        return view;
    }


}
