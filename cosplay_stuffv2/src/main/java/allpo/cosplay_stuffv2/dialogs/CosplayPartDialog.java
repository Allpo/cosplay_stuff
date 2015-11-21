package allpo.cosplay_stuffv2.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Currency;
import java.util.Locale;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.ProjectActivity;
import allpo.cosplay_stuffv2.models.CosplayPart;
import allpo.cosplay_stuffv2.models.CosplayProject;
import allpo.cosplay_stuffv2.receivers.PartReceiver;

/**
 * Created by Allpo on 06/07/2015.
 */
public class CosplayPartDialog extends DialogFragment implements View.OnClickListener {

    private CosplayPart mPart;
    private CosplayProject mProject;

    private EditText mTitle;
    private Switch mType;
    private EditText mValue;
    private EditText mDescription;
    private TextView mCurrency;

    private Button mSave;
    private Button mDismiss;
    private Button mDelete;

    public static final String PART_ARG = "CosplayPartDialog.PART_ARG";

    public static CosplayPartDialog startForPart(CosplayPart part) throws IllegalAccessException {
        if (part == null)
            throw new IllegalAccessException("Part can't be null to display this dialog");
        CosplayPartDialog dialog = startNewPart();

        Bundle args = new Bundle();
        args.putLong(PART_ARG, part.getId());

        return dialog;
    }

    public static CosplayPartDialog startNewPart() {
        CosplayPartDialog dialog = new CosplayPartDialog();

        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null && args.containsKey(PART_ARG)) {
            mPart = CosplayPart.findById(CosplayPart.class, args.getLong(PART_ARG));

            if (mPart == null) {
                throw new IllegalArgumentException("Part was nos found, please provide a valid part Id");
            }
        } else {
            mPart = new CosplayPart();
            mPart.setProjectId(mProject.getId());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_part, null, false);

        setCancelable(false);

        mTitle = (EditText) view.findViewById(R.id.dialog_part_title);
        mType = (Switch) view.findViewById(R.id.dialog_part_toggle);
        mValue = (EditText) view.findViewById(R.id.dialog_part_price_time);
        mDescription = (EditText) view.findViewById(R.id.dialog_part_description);
        mCurrency = (TextView) view.findViewById(R.id.dialog_part_currency);

        mDelete = (Button) view.findViewById(R.id.dialog_part_delete);
        mSave = (Button) view.findViewById(R.id.dialog_part_save);
        mDismiss = (Button) view.findViewById(R.id.dialog_part_dismiss);

        initUI();

        return view;
    }

    private void initUI() {
        if (mPart.getId() == null || mPart.getId() == 0) {
            mDelete.setVisibility(View.GONE);
        }
        mDelete.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mDismiss.setOnClickListener(this);

        mType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isCrafted) {
                changeType(isCrafted);
            }
        });

        changeType(mType.isChecked());
    }

    private void changeType(boolean isCrafted) {
        if (isCrafted) {
            mCurrency.setText(getString(R.string.dialog_currency_time));
            mValue.setHint(R.string.dialog_hint_time);
        } else {
            Currency currency = Currency.getInstance(Locale.getDefault());
            mCurrency.setText(currency.getSymbol());
            mValue.setHint(R.string.dialog_hint_price);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof ProjectActivity) {
            mProject = ((ProjectActivity) getActivity()).getProject();

            if (mProject == null)
                throw new IllegalArgumentException("Project from activity can't be null");
        } else {
            throw new IllegalStateException("Calling activity must be ProjectActivity");
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.dialog_part_save:
                if (mPart.getId() == null || mPart.getId() == 0) {
                    mProject.addPart(mPart);
                }
                savePart();
                dismiss();
                break;
            case R.id.dialog_part_delete:
                mPart.delete();
                dismiss();
                break;
            case R.id.dialog_part_dismiss:
                dismiss();
                break;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.dialog_cosplay_part_title);

        return dialog;
    }

    private void savePart() {
        mPart.setTitle(mTitle.getText().toString());
        if (mType.isChecked()) {
            //Craft
            if (!mValue.getText().toString().isEmpty()) {
                mPart.setTime(Float.parseFloat(mValue.getText().toString()));
            }
            mPart.setPartType(CosplayPart.CRAFT);
        } else {
            //Buy
            if (!mValue.getText().toString().isEmpty()) {
                mPart.setPrice(Float.parseFloat(mValue.getText().toString()));
            }
            mPart.setPartType(CosplayPart.BUY);
        }
        mPart.setDescription(mDescription.getText().toString());

        mPart.save();
        PartReceiver.sendCreatePart(getActivity(), mPart);
    }
}
