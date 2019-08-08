package feedback.mpnsc.exisitingconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import feedback.mpnsc.DataHolderClass;
import feedback.mpnsc.R;

/**
 * Created by swatiG on 02-07-2015.
 */
public class Existing_CurrentDetailtest extends Activity {
    CheckBox same_add;
    EditText house_no, street, city, block, gp, pin_no, email,
            mobile, landline, plot_no, building_name, holding_khata_no, ward_no, address, landmark, name, middle, last, father_name;
    String str_house_no, str_street, str_city, str_district, str_block,
            str_gp, str_pin_no, str_email,
            str_mobile, str_landline, str_plot_no, str_building_name, str_holding_khata_no, str_ward_no, str_address, str_landmark,
            str_name, str_middle, str_last, str_father_name, str_title;
    Spinner title, district;
    int title_position;
    Spannable Sp_fName,Sp_block,Sp_address,Sp_pincode,Sp_landmark,Sp_village,Sp_mobile;
    TextView tv_fname,tv_block,tv_address,tv_pincode,tv_landmark,tv_village,tv_mobile;
    int result;
    Button submit;
    TextView TVTicketnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_add);
        callView();
        TVTicketnum.setText(DataHolderClass.getInstance().get_new_meter_ticket_no());
        Log.e("house_no", DataHolderExisting.getInstance().getPlot());

        if (DataHolderExisting.getInstance().getName().equals("null")) {
            name.setText("");
        } else
            name.setText(DataHolderExisting.getInstance().getName());

        if (DataHolderExisting.getInstance().getMiddle_name().equals("null")) {
            middle.setText("");
        } else
            middle.setText(DataHolderExisting.getInstance().getMiddle_name());

        if (DataHolderExisting.getInstance().getLast_name().equals("null")) {
            last.setText("");
        } else
            last.setText(DataHolderExisting.getInstance().getLast_name());

        if (DataHolderExisting.getInstance().getFather_husband_name().equals("null")) {
            father_name.setText("");
        } else
            father_name.setText(DataHolderExisting.getInstance().getFather_husband_name());

        // plot_no=(EditText)findViewById(R.id.et_plotno);
        if (DataHolderExisting.getInstance().getPlot().equals("null")) {
            house_no.setText("");
        } else
            house_no.setText(DataHolderExisting.getInstance().getPlot());

        //house_no=(EditText)findViewById(R.id.et_houseno);
        if (DataHolderExisting.getInstance().getBuilding().equals("null")) {
            plot_no.setText("");
        } else
            plot_no.setText(DataHolderExisting.getInstance().getBuilding());

        //building_name=(EditText)findViewById(R.id.et_buildng_name);
        if (DataHolderExisting.getInstance().getHousename().equals("null")) {
            building_name.setText("");
        } else
            building_name.setText(DataHolderExisting.getInstance().getHousename());

        //holding_khata_no=(EditText)findViewById(R.id.et_holding_khata_no);
        if (DataHolderExisting.getInstance().getKhatano().equals("null")) {
            holding_khata_no.setText("");
        } else
            holding_khata_no.setText(DataHolderExisting.getInstance().getKhatano());

        // ward_no=(EditText)findViewById(R.id.et_ward_no);
        if (DataHolderExisting.getInstance().getWard().equals("null")) {
            ward_no.setText("");
        } else
            ward_no.setText(DataHolderExisting.getInstance().getWard());

        //street=(EditText)findViewById(R.id.et_street);
        if (DataHolderExisting.getInstance().getStreet().equals("null")) {
            street.setText("");
        } else
            street.setText(DataHolderExisting.getInstance().getStreet());

        //block=(EditText)findViewById(R.id.et_block);
        if (DataHolderExisting.getInstance().getBlock().equals("null")) {
            block.setText("");
        } else
            block.setText(DataHolderExisting.getInstance().getBlock());

        //gp=(EditText)findViewById(R.id.et_gp);
        if (DataHolderExisting.getInstance().getGP().equals("null")) {
            gp.setText("");
        } else
            gp.setText(DataHolderExisting.getInstance().getGP());

        //address=(EditText)findViewById(R.id.et_address);
        if (DataHolderExisting.getInstance().getAddress1().equals("null")) {
            address.setText("");
        } else
            address.setText(DataHolderExisting.getInstance().getAddress1());

        //pin_no=(EditText)findViewById(R.id.et_pin);
        if (DataHolderExisting.getInstance().getpin().equals("null")) {
            pin_no.setText("");
        } else
            pin_no.setText(DataHolderExisting.getInstance().getpin());

        //landmark=(EditText)findViewById(R.id.et_landmark);
        if (DataHolderExisting.getInstance().getAddress2().equals("null")) {
            landmark.setText("");
        } else
            landmark.setText(DataHolderExisting.getInstance().getAddress2());

        //city=(EditText)findViewById(R.id.et_city);
        if (DataHolderExisting.getInstance().getVillage().equals("null")) {
            city.setText("");
        } else
            city.setText(DataHolderExisting.getInstance().getVillage());

        //district=(EditText)findViewById(R.id.et_district);
       /* if(DataHolderExisting.getInstance().getDistrict().equals("null")){
            district.setText("");
        }else
            district.setText(DataHolderExisting.getInstance().getDistrict());*/

        //email=(EditText)findViewById(R.id.et_email_id);
        if (DataHolderExisting.getInstance().getEmailid().equals("null")) {
            email.setText("");
        } else
            email.setText(DataHolderExisting.getInstance().getEmailid());

        //mobile=(EditText)findViewById(R.id.et_mobile);
        if (DataHolderExisting.getInstance().getMobile_no().equals("null")) {
            mobile.setText("");
        } else
            mobile.setText(DataHolderExisting.getInstance().getMobile_no());

        //landline=(EditText)findViewById(R.id.et_landline);
        if (DataHolderExisting.getInstance().getLand_line_no().equals("null")) {
            landline.setText("");
        } else
            landline.setText(DataHolderExisting.getInstance().getLand_line_no());

        tv_fname=(TextView)findViewById(R.id.tv_father);
        tv_block=(TextView)findViewById(R.id.tv_block);
        tv_address=(TextView)findViewById(R.id.tv_address);
        tv_pincode=(TextView)findViewById(R.id.tv_pin);
        tv_landmark=(TextView)findViewById(R.id.tv_landmark);
        tv_village=(TextView)findViewById(R.id.tv_city);
        tv_mobile=(TextView)findViewById(R.id.tv_mobile);

        Sp_fName = new SpannableString(tv_fname.getText().toString());
        Sp_block = new SpannableString(tv_block.getText().toString());
        Sp_address= new SpannableString(tv_address.getText().toString());
        Sp_pincode = new SpannableString(tv_pincode.getText().toString());
        Sp_landmark = new SpannableString(tv_landmark.getText().toString());
        Sp_village = new SpannableString(tv_village.getText().toString());
        Sp_mobile = new SpannableString(tv_mobile.getText().toString());
        Sp_fName.setSpan(new ForegroundColorSpan(Color.RED), 11, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Sp_block.setSpan(new ForegroundColorSpan(Color.RED), 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Sp_address.setSpan(new ForegroundColorSpan(Color.RED), 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Sp_pincode.setSpan(new ForegroundColorSpan(Color.RED), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Sp_landmark.setSpan(new ForegroundColorSpan(Color.RED), 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Sp_village.setSpan(new ForegroundColorSpan(Color.RED), 17, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Sp_mobile.setSpan(new ForegroundColorSpan(Color.RED), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_fname.setText(Sp_fName);
        tv_block.setText(Sp_block);
        tv_address.setText(Sp_address);
        tv_pincode.setText(Sp_pincode);
        tv_landmark.setText(Sp_landmark);
        tv_village.setText(Sp_village);
        tv_mobile.setText(Sp_mobile);

        father_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                father_name.setBackgroundColor(getResources().getColor(R.color.themecolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                father_name.setBackgroundColor(getResources().getColor(R.color.themecolor));
            }
        });

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                city.setBackgroundColor(getResources().getColor(R.color.themecolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                city.setBackgroundColor(getResources().getColor(R.color.themecolor));
            }
        });


        pin_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pin_no.setBackgroundColor(getResources().getColor(R.color.themecolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pin_no.setBackgroundColor(getResources().getColor(R.color.themecolor));
            }
        });

        block.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                block.setBackgroundColor(getResources().getColor(R.color.themecolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                block.setBackgroundColor(getResources().getColor(R.color.themecolor));
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                address.setBackgroundColor(getResources().getColor(R.color.themecolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                address.setBackgroundColor(getResources().getColor(R.color.themecolor));
            }
        });
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mobile.setBackgroundColor(getResources().getColor(R.color.themecolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mobile.setBackgroundColor(getResources().getColor(R.color.themecolor));
            }
        });
        landmark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                landmark.setBackgroundColor(getResources().getColor(R.color.themecolor));

            }

            @Override
            public void afterTextChanged(Editable editable) {
                landmark.setBackgroundColor(getResources().getColor(R.color.themecolor));
            }
        });




        submit = (Button) findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_position = title.getSelectedItemPosition();
                str_title = title.getSelectedItem().toString();
                str_name = name.getText().toString().trim();
                str_middle = middle.getText().toString().trim();
                str_last = last.getText().toString().trim();
                str_father_name = father_name.getText().toString().trim();

                str_plot_no = plot_no.getText().toString().trim();
                str_house_no = house_no.getText().toString().trim();
                str_building_name = building_name.getText().toString().trim();
                str_holding_khata_no = holding_khata_no.getText().toString().trim();
                str_ward_no = ward_no.getText().toString().trim();
                str_street = street.getText().toString().trim();
                str_block = block.getText().toString().trim();
                str_gp = gp.getText().toString().trim();
                str_address = address.getText().toString().trim();
                str_pin_no = pin_no.getText().toString().trim();
                str_landmark = landmark.getText().toString().trim();
                str_city = city.getText().toString().trim();
                str_district = district.getSelectedItem().toString();
                str_email = email.getText().toString().trim();
                str_mobile = mobile.getText().toString().trim();
                str_landline = landline.getText().toString().trim();




                //str_mobile=mobile.getText().toString().trim();
/*
                int result = Integer.parseInt(str_mobile.substring(0,1));

                if ((result < 7) || (result > 9)) {
                    Toast.makeText(Existing_CurrentDetailtest.this, "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                }*/

                 if (str_name.equals("")){name.setBackgroundColor(Color.parseColor("#412b55")); }
                //else if (str_middle.equals("")){middle.setBackgroundColor(Color.YELLOW);}
                //else if (str_last.equals("")){last.setBackgroundColor(Color.YELLOW); }
                else if (str_father_name.equals("")){father_name.setBackgroundColor(Color.parseColor("#412b55"));}

               /* else if(str_plot_no.equals("")){ plot_no.setBackgroundColor(Color.YELLOW);}
                else if (str_house_no.equals("")){house_no.setBackgroundColor(Color.YELLOW); }
                else if (str_building_name.equals("")){building_name.setBackgroundColor(Color.YELLOW);}
                else if (str_holding_khata_no.equals("")){holding_khata_no.setBackgroundColor(Color.YELLOW); }
                else if (str_ward_no.equals("")){ward_no.setBackgroundColor(Color.YELLOW);}
                else if (str_street.equals("")){street.setBackgroundColor(Color.YELLOW); }*/
                else if (str_block.equals("")){block.setBackgroundColor(Color.parseColor("#412b55"));}
                 else if (str_pin_no.equals("")){pin_no.setBackgroundColor(Color.parseColor("#412b55"));}
               // else if (str_gp.equals("")){gp.setBackgroundColor(Color.YELLOW);}
                else if (str_address.equals("")){address.setBackgroundColor(Color.parseColor("#412b55")); }
                else if (str_landmark.equals("")){landmark.setBackgroundColor(Color.parseColor("#412b55"));}
                else if (str_city.equals("")){city.setBackgroundColor(Color.parseColor("#412b55"));}
               // else if (district.getSelectedItem().toString().trim().equals("Select District")){district.setBackgroundColor(Color.YELLOW); }
                //else if (str_email.equals("")){email.setBackgroundColor(Color.YELLOW);}
                else if (str_mobile.equals("") || str_mobile.length()!=10){mobile.setBackgroundColor(Color.parseColor("#412b55"));}
                else if((Integer.parseInt(str_mobile.substring(0,1)) < 7) || (Integer.parseInt(str_mobile.substring(0,1)) > 9)){
                    Toast.makeText(getApplicationContext(), "Mobile number must be start form 7,8,9 Number", Toast.LENGTH_SHORT).show();
                }
                //else if (str_landline.equals("")){landline.setBackgroundColor(Color.YELLOW);}
                else {
                    Intent i = new Intent(Existing_CurrentDetailtest.this, Existing_DataFinish.class);
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ShowAlertonBack();
    }

    public void ShowAlertonBack() {
        Existing_CurrentDetailtest.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Existing_CurrentDetailtest.this,R.style.MyAlertDialogStyle);
                builder.setTitle("Are you sure to go back:");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Existing_CurrentDetailtest.this, ExistingConsumer_Search.class));
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void callView() {
        TVTicketnum = (TextView) findViewById(R.id.textViewTicket);
        title = (Spinner) findViewById(R.id.spinner9);
        name = (EditText) findViewById(R.id.editText1);
        middle = (EditText) findViewById(R.id.editmiddle);
        last = (EditText) findViewById(R.id.editlast);
        father_name = (EditText) findViewById(R.id.editText2);

        plot_no = (EditText) findViewById(R.id.et_plotno);
        house_no = (EditText) findViewById(R.id.et_houseno);
        building_name = (EditText) findViewById(R.id.et_buildng_name);
        holding_khata_no = (EditText) findViewById(R.id.et_holding_khata_no);
        ward_no = (EditText) findViewById(R.id.et_ward_no);
        street = (EditText) findViewById(R.id.et_street);
        block = (EditText) findViewById(R.id.et_block);
        gp = (EditText) findViewById(R.id.et_gp);
        address = (EditText) findViewById(R.id.et_address);
        pin_no = (EditText) findViewById(R.id.et_pin);
        landmark = (EditText) findViewById(R.id.et_landmark);
        city = (EditText) findViewById(R.id.et_city);
        //district=(EditText)findViewById(R.id.et_district);
        district = (Spinner) findViewById(R.id.et_district);
        email = (EditText) findViewById(R.id.et_email_id);
        mobile = (EditText) findViewById(R.id.et_mobile);
        landline = (EditText) findViewById(R.id.et_landline);
        same_add = (CheckBox) findViewById(R.id.checkBox);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("from cons", "onPause");
        title_position = title.getSelectedItemPosition();
        str_title = title.getSelectedItem().toString();
        str_name = name.getText().toString().trim();
        str_middle = middle.getText().toString().trim();
        str_last = last.getText().toString().trim();
        str_father_name = father_name.getText().toString().trim();

        str_plot_no = plot_no.getText().toString().trim();
        str_house_no = house_no.getText().toString().trim();
        str_building_name = building_name.getText().toString().trim();
        str_holding_khata_no = holding_khata_no.getText().toString().trim();
        str_ward_no = ward_no.getText().toString().trim();
        str_street = street.getText().toString().trim();
        str_block = block.getText().toString().trim();
        str_gp = gp.getText().toString().trim();
        str_address = address.getText().toString().trim();
        str_pin_no = pin_no.getText().toString().trim();
        str_landmark = landmark.getText().toString().trim();
        str_city = city.getText().toString().trim();
        str_district = district.getSelectedItem().toString();
        str_email = email.getText().toString().trim();
        str_mobile = mobile.getText().toString().trim();
        str_landline = landline.getText().toString().trim();
        // Log.e("str_mobile",""+str_mobile);
        DataHolderClass.getInstance().set_title(str_title);
        DataHolderClass.getInstance().set_name(str_name);
        DataHolderClass.getInstance().setStr_middle_name(str_middle);
        DataHolderClass.getInstance().setStr_last_name(str_last);
        DataHolderClass.getInstance().set_father_name(str_father_name);

        DataHolderClass.getInstance().set_bulding_no(str_plot_no);
        DataHolderClass.getInstance().setHouse_flatno(str_house_no);
        DataHolderClass.getInstance().setHousename(str_building_name);
        DataHolderClass.getInstance().setKhata_no(str_holding_khata_no);
        DataHolderClass.getInstance().setWard_no(str_ward_no);
        DataHolderClass.getInstance().set_street(str_street);
        DataHolderClass.getInstance().set_block(str_block);
        DataHolderClass.getInstance().set_gp(str_gp);
        DataHolderClass.getInstance().setStr_adres1(str_address);
        DataHolderClass.getInstance().set_pin_no(str_pin_no);
        DataHolderClass.getInstance().setLandmark(str_landmark);
        DataHolderClass.getInstance().set_city(str_city);
        DataHolderClass.getInstance().set_district(str_district);
        DataHolderClass.getInstance().set_email(str_email);
        DataHolderClass.getInstance().set_mobile(str_mobile);
        DataHolderClass.getInstance().set_landline(str_landline);
    }

}