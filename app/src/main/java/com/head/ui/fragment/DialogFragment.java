package com.head.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.head.dialog.HeadDialog;
import com.head.dialog.dialogs.BottomDialog;
import com.head.dialog.dialogs.BottomMenu;
import com.head.dialog.dialogs.CustomDialog;
import com.head.dialog.dialogs.FullScreenDialog;
import com.head.dialog.dialogs.InputDialog;
import com.head.dialog.dialogs.MessageDialog;
import com.head.dialog.dialogs.PopTip;
import com.head.dialog.dialogs.TimePickerDialog;
import com.head.dialog.dialogs.TipDialog;
import com.head.dialog.dialogs.WaitDialog;
import com.head.dialog.interfaces.OnBackPressedListener;
import com.head.dialog.interfaces.OnBindView;
import com.head.dialog.interfaces.OnDialogButtonClickListener;
import com.head.dialog.interfaces.OnInputDialogButtonClickListener;
import com.head.dialog.interfaces.OnMenuItemClickListener;
import com.head.dialog.interfaces.OnMenuItemSelectListener;
import com.head.dialog.interfaces.OnTimeSelectChangeListener;
import com.head.dialog.interfaces.OnTimeSelectListener;
import com.head.dialog.style.IOSStyle;
import com.head.dialog.style.MaterialStyle;
import com.head.dialog.util.InputInfo;
import com.head.ui.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class DialogFragment extends Fragment implements View.OnClickListener {


    protected View rootView;
    protected RadioButton rbMaterial;
    protected RadioButton rbIos;
    protected RadioGroup rGStyle;
    protected RadioButton rbLight;
    protected RadioButton rbDark;
    protected RadioGroup rgTheme;
    protected TextView btMessageDialog;
    protected TextView btnSelectDialog;
    protected TextView btnInputDialog;
    protected TextView btnWaitDialog;
    protected TextView btnWaitAndTipDialog;
    protected TextView btnTipSuccess;
    protected TextView btnTipWarning;
    protected TextView btnTipError;
    protected TextView btnTipProgress;
    protected TextView btnPoptip;
    protected TextView btnPoptipBigMessage;
    protected TextView btnBottomDialog;
    protected TextView btnBottomMenu;
    protected TextView btnBottomReply;
    protected TextView btnBottomSelectMenu;
    protected TextView btnCustomMessageDialog;
    protected TextView btnCustomInputDialog;
    protected TextView btnCustomBottomMenu;
    protected TextView btnCustomDialog;
    protected TextView btnFullScreenDialogLogin,btn_bottom_mu_select_menu;
    protected TextView btnTime;

    float progress;
    private int selectMenuIndex;
    int[] selectMenuIndexArray;

    private TextView btnReplyCommit;
    private EditText editReplyCommit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HeadDialog.globalStyle = MaterialStyle.style();
        HeadDialog.globalTheme = HeadDialog.THEME.LIGHT;
        HeadDialog.onlyOnePopTip = false;
        initEvents();
    }

    private void initEvents() {
        rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbLight:
                        HeadDialog.globalTheme = HeadDialog.THEME.LIGHT;
                        break;
                    case R.id.rbDark:
                        HeadDialog.globalTheme = HeadDialog.THEME.DARK;
                        break;
                }
            }
        });

        rGStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                HeadDialog.cancelButtonText = "??????";
                switch (checkedId) {
                    case R.id.rbMaterial:
                        HeadDialog.globalStyle = MaterialStyle.style();
                        HeadDialog.cancelButtonText = "";
                        break;
                    case R.id.rbIos:
                        HeadDialog.globalStyle = IOSStyle.style();
                        break;
                }
            }
        });


    }


    private void initView(View rootView) {
        rbMaterial = (RadioButton) rootView.findViewById(R.id.rbMaterial);
        rbIos = (RadioButton) rootView.findViewById(R.id.rbIos);
        rGStyle = (RadioGroup) rootView.findViewById(R.id.rGStyle);
        rbLight = (RadioButton) rootView.findViewById(R.id.rbLight);
        rbDark = (RadioButton) rootView.findViewById(R.id.rbDark);
        rgTheme = (RadioGroup) rootView.findViewById(R.id.rgTheme);
        btMessageDialog = (TextView) rootView.findViewById(R.id.btMessageDialog);
        btMessageDialog.setOnClickListener(DialogFragment.this);
        btnSelectDialog = (TextView) rootView.findViewById(R.id.btn_selectDialog);
        btnSelectDialog.setOnClickListener(DialogFragment.this);
        btnInputDialog = (TextView) rootView.findViewById(R.id.btn_inputDialog);
        btnInputDialog.setOnClickListener(DialogFragment.this);
        btnWaitDialog = (TextView) rootView.findViewById(R.id.btn_waitDialog);
        btnWaitDialog.setOnClickListener(DialogFragment.this);
        btnWaitAndTipDialog = (TextView) rootView.findViewById(R.id.btn_waitAndTipDialog);
        btnWaitAndTipDialog.setOnClickListener(DialogFragment.this);
        btnTipSuccess = (TextView) rootView.findViewById(R.id.btn_tipSuccess);
        btnTipSuccess.setOnClickListener(DialogFragment.this);
        btnTipWarning = (TextView) rootView.findViewById(R.id.btn_tipWarning);
        btnTipWarning.setOnClickListener(DialogFragment.this);
        btnTipError = (TextView) rootView.findViewById(R.id.btn_tipError);
        btnTipError.setOnClickListener(DialogFragment.this);
        btnTipProgress = (TextView) rootView.findViewById(R.id.btn_tipProgress);
        btnTipProgress.setOnClickListener(DialogFragment.this);
        btnPoptip = (TextView) rootView.findViewById(R.id.btn_poptip);
        btnPoptip.setOnClickListener(DialogFragment.this);
        btnPoptipBigMessage = (TextView) rootView.findViewById(R.id.btn_poptip_bigMessage);
        btnPoptipBigMessage.setOnClickListener(DialogFragment.this);
        btnBottomDialog = (TextView) rootView.findViewById(R.id.btn_bottom_dialog);
        btnBottomDialog.setOnClickListener(DialogFragment.this);
        btnBottomMenu = (TextView) rootView.findViewById(R.id.btn_bottom_menu);
        btnBottomMenu.setOnClickListener(DialogFragment.this);
        btnBottomReply = (TextView) rootView.findViewById(R.id.btn_bottom_reply);
        btnBottomReply.setOnClickListener(DialogFragment.this);
        btnBottomSelectMenu = (TextView) rootView.findViewById(R.id.btn_bottom_select_menu);
        btnBottomSelectMenu.setOnClickListener(DialogFragment.this);
        btnCustomMessageDialog = (TextView) rootView.findViewById(R.id.btn_customMessageDialog);
        btnCustomMessageDialog.setOnClickListener(DialogFragment.this);
        btnCustomInputDialog = (TextView) rootView.findViewById(R.id.btn_customInputDialog);
        btnCustomInputDialog.setOnClickListener(DialogFragment.this);
        btnCustomBottomMenu = (TextView) rootView.findViewById(R.id.btn_customBottomMenu);
        btnCustomBottomMenu.setOnClickListener(DialogFragment.this);
        btnCustomDialog = (TextView) rootView.findViewById(R.id.btn_customDialog);
        btnCustomDialog.setOnClickListener(DialogFragment.this);
        btnFullScreenDialogLogin = (TextView) rootView.findViewById(R.id.btn_fullScreenDialog_login);
        btnFullScreenDialogLogin.setOnClickListener(DialogFragment.this);
        btnTime = (TextView) rootView.findViewById(R.id.btn_Time);
        btnTime.setOnClickListener(DialogFragment.this);
        btn_bottom_mu_select_menu = (TextView) rootView.findViewById(R.id.btn_bottom_mu_select_menu);
        btn_bottom_mu_select_menu.setOnClickListener(DialogFragment.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btMessageDialog) {
            MessageDialog.show("??????", "????????????????????????", "??????").setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                @Override
                public boolean onClick(MessageDialog baseDialog, View v) {
                    PopTip.show("??????????????????");
                    return false;
                }
            });

        } else if (view.getId() == R.id.btn_selectDialog) {

            MessageDialog messageDialog = new MessageDialog("???????????????", "??????App??????????????????????????????????????????????????????", "??????App", "??????", "??????App?????????")
                    .setButtonOrientation(LinearLayout.VERTICAL);
            messageDialog.show();

        } else if (view.getId() == R.id.btn_inputDialog) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setSelectAllText(true);
            new InputDialog("??????", "????????????", "??????", "??????", "?????????????????????")
                    .setInputText("Hello World")
                    .setCancelable(false)
                    .setInputInfo(inputInfo)
                    .setOkButton(new OnInputDialogButtonClickListener<InputDialog>() {
                        @Override
                        public boolean onClick(InputDialog baseDialog, View v, String inputStr) {
                            PopTip.show("??????????????????" + inputStr);
                            return false;
                        }
                    })
                    .show();

        } else if (view.getId() == R.id.btn_waitDialog) {
            WaitDialog.show("Please Wait!").setOnBackPressedListener(new OnBackPressedListener() {
                @Override
                public boolean onBackPressed() {
                    PopTip.show("????????????");
                    return false;
                }
            }).setCancelable(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WaitDialog.dismiss();
                }
            }, 5000);

        } else if (view.getId() == R.id.btn_waitAndTipDialog) {
            WaitDialog.show("Please Wait!").setOnBackPressedListener(new OnBackPressedListener() {
                @Override
                public boolean onBackPressed() {
                    PopTip.show("????????????");
                    return false;
                }
            });
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TipDialog.show("Success!", WaitDialog.TYPE.SUCCESS);
                }
            }, 2000);

        } else if (view.getId() == R.id.btn_tipSuccess) {
            TipDialog.show("Success!", WaitDialog.TYPE.SUCCESS);

        } else if (view.getId() == R.id.btn_tipWarning) {
            TipDialog.show("Warning!", WaitDialog.TYPE.WARNING);

        } else if (view.getId() == R.id.btn_tipError) {
            TipDialog.show("Error!", WaitDialog.TYPE.ERROR);

        } else if (view.getId() == R.id.btn_tipProgress) {
            // ??????1 = ????????????????????? ???
            // ??????2 = ???????????????
            // ??????3 = ???1??????????????????????????????
            // ??????4 = ?????????????????????
            // ??????5 = ????????????
            Observable.intervalRange(1, 10, 1000, 300, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            WaitDialog.show("????????????...");
                            progress = 0;
                        }

                        @Override
                        public void onNext(Long value) {
                            progress = progress + 0.1f;

                            if (progress < 1f) {
                                WaitDialog.show("????????????" + ((int) (progress * 100)) + "%", progress);
                            } else {
                                TipDialog.show("????????????", WaitDialog.TYPE.SUCCESS);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            TipDialog.show("????????????", WaitDialog.TYPE.ERROR);

                        }

                        @Override
                        public void onComplete() {
                        }

                    });

        } else if (view.getId() == R.id.btn_poptip) {

            PopTip.show("??????????????????");

        } else if (view.getId() == R.id.btn_poptip_bigMessage) {
            PopTip.show(R.mipmap.ic_launcher, "??????").setAutoTintIconInLightOrDarkMode(false).showLong();


        } else if (view.getId() == R.id.btn_bottom_dialog) {
            String s = "???????????????????????????????????????????????????????????????";
            new BottomDialog("??????", "???????????????????????????\n" + s + "???\n????????????????????????????????????????????????????????????",
                    new OnBindView<BottomDialog>(R.layout.layout_custom_view) {
                        @Override
                        public void onBind(BottomDialog dialog, View v) {

                        }
                    })
//                    .setCancelButton("??????")
                    .show();

        } else if (view.getId() == R.id.btn_bottom_menu) {
            WaitDialog.show("");
            TipDialog.show("");
            String[] strings = new String[]{"??????\nssss\nssss\nssss\nssss\nssss\nssss\nssss\nssss\nssss\nssss\nssss\nssss", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "?????????", "????????????", "????????????", "????????????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "?????????", "????????????", "????????????", "????????????"};
            BottomMenu.build()
                    .setMenuList(strings)
                    .setStyle(new MaterialStyle())

                    .setCancelButton("??????")
                    .setOnMenuItemClickListener(new OnMenuItemClickListener<BottomMenu>() {
                        @Override
                        public boolean onClick(BottomMenu dialog, CharSequence text, int index) {
                            PopTip.show(text);
                            Log.e("====",text.toString());
                            return false;
                        }
                    }).show();

        } else if (view.getId() == R.id.btn_bottom_reply) {
            BottomDialog.show(new OnBindView<BottomDialog>(R.layout.layout_custom_reply) {
                @Override
                public void onBind(final BottomDialog dialog, View v) {
                    btnReplyCommit = v.findViewById(R.id.btn_reply_commit);
                    editReplyCommit = v.findViewById(R.id.edit_reply_commit);
                    btnReplyCommit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            PopTip.show("???????????????\n" + editReplyCommit.getText().toString());
                        }
                    });
                    editReplyCommit.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editReplyCommit.requestFocus();
                            editReplyCommit.setFocusableInTouchMode(true);
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(editReplyCommit, InputMethodManager.RESULT_UNCHANGED_SHOWN);
                        }
                    }, 300);
                }
            }).setAllowInterceptTouch(false);


        } else if (view.getId() == R.id.btn_bottom_select_menu) {
            BottomMenu.show(new String[]{"??????", "??????", "????????????", "?????????????????????"})
                    .setMessage("???????????????????????????????????????????????????????????????")
                    .setTitle("??????????????????")
                    .setOnMenuItemClickListener(new OnMenuItemSelectListener<BottomMenu>() {
                        @Override
                        public void onOneItemSelect(BottomMenu dialog, CharSequence text, int index, boolean select) {
                            selectMenuIndex = index;
                            PopTip.show(text);
                        }
                    }).setOkButton("??????", new OnDialogButtonClickListener<BottomDialog>() {
                @Override
                public boolean onClick(BottomDialog baseDialog, View v) {
                    PopTip.show("????????????");
                    return false;
                }
            })
                    .setSelection(selectMenuIndex);

        }else if (view.getId() == R.id.btn_bottom_mu_select_menu){
            BottomMenu.show(new String[]{"??????", "??????","??????","??????","??????","??????","??????","??????","??????","??????","??????","??????","??????","??????","??????","??????","??????", "????????????", "?????????????????????"})
                    .setMessage("???????????????????????????????????????????????????????????????")
                    .setTitle("??????????????????")
                    .setOnMenuItemClickListener(new OnMenuItemSelectListener<BottomMenu>() {
                        @Override
                        public void onMultiItemSelect(BottomMenu dialog, CharSequence[] text, int[] index) {
                            PopTip.show("????????????" +text);
                            selectMenuIndexArray=index;
                        }
                    })
                    .setOkButton("??????", new OnDialogButtonClickListener<BottomDialog>() {
                        @Override
                        public boolean onClick(BottomDialog baseDialog, View v) {
                            PopTip.show("????????????" );
                            return false;
                        }
                    })
                    .setSelection(selectMenuIndexArray);
        }

        else if (view.getId() == R.id.btn_customMessageDialog) {
            MessageDialog.show("???????????????", "???????????????????????????????????????????????????????????????", "??????", "??????")
                    .setCustomView(new OnBindView<MessageDialog>(R.layout.layout_custom_view) {
                        @Override
                        public void onBind(MessageDialog dialog, View v) {
                        }
                    });

        } else if (view.getId() == R.id.btn_customInputDialog) {
            InputDialog.show("???????????????", "???????????????????????????????????????????????????????????????", "??????", "??????")
                    .setCustomView(new OnBindView<MessageDialog>(R.layout.layout_custom_view) {
                        @Override
                        public void onBind(MessageDialog dialog, View v) {

                        }
                    });

        } else if (view.getId() == R.id.btn_customBottomMenu) {
            BottomMenu.show(new String[]{"?????????????????????", "????????????", "??????????????????"})
                    .setMessage("http://www.kongzue.com/DialogX")
                    .setOnMenuItemClickListener(new OnMenuItemClickListener<BottomMenu>() {
                        @Override
                        public boolean onClick(BottomMenu dialog, CharSequence text, int index) {
                            PopTip.show(text);
                            return false;
                        }
                    })
                    .setCustomView(new OnBindView<BottomDialog>(R.layout.layout_custom_view) {
                        @Override
                        public void onBind(BottomDialog dialog, View v) {

                        }
                    });

        } else if (view.getId() == R.id.btn_customDialog) {
            CustomDialog.show(new OnBindView<CustomDialog>(R.layout.layout_custom_dialog) {
                @Override
                public void onBind(final CustomDialog dialog, View v) {
                    ImageView btnOk;
                    btnOk = v.findViewById(R.id.btn_ok);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            })
                    .setAlign(CustomDialog.ALIGN.CENTER)
                    .setMaskColor(getResources().getColor(R.color.black30));

        } else if (view.getId() == R.id.btn_fullScreenDialog_login) {
            FullScreenDialog.show(new OnBindView<FullScreenDialog>(R.layout.layout_custom_view) {
                @Override
                public void onBind(FullScreenDialog dialog, View v) {
                }
            });


        } else if (view.getId() == R.id.btn_Time) {
            TimePickerDialog.show(new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    SimpleDateFormat format1 =
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    PopTip.show(format1.format(date));
                }
            }).setOnTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                @Override
                public void onTimeSelectChanged(Date date) {
                    SimpleDateFormat format1 =
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    PopTip.show(format1.format(date));
                }
            }).setDate(transformationDate("2021-06-13"));
            ;

        }
    }
    public static Calendar transformationDate(String time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

        Date date= null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
