package app.fit.fitndflow.ui.features.training;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fit.fitndflow.R;

import app.fit.fitndflow.domain.model.SerieModel;
import app.fit.fitndflow.ui.features.common.AccessibilityInterface;
import app.fit.fitndflow.ui.features.common.AccessibilityUtils;

public class EditableBtnDeleteSerie extends LinearLayout implements AccessibilityInterface {

    private EditText editTxtReps;
    private EditText editTxtKg;
    private ImageButton deleteBtn;

    public EditableBtnDeleteSerie(Context context, SerieEditableListener serieEditableListener, SerieModel serie,  int position) {
        super(context);
        View view = inflate(context, R.layout.component_editable_btn_delete_serie_training, this);
        bindViews(view);
        setClickListener(serieEditableListener, position);
        printSerie(serie);
        initAccessibility();
        editTxtReps.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));
        editTxtKg.addTextChangedListener(AccessibilityUtils.createTextWatcher(this));

    }

    private void bindViews(View view){
        editTxtReps = view.findViewById(R.id.repsEdTxtDelete);
        editTxtKg = view.findViewById(R.id.kgEdTxtDelete);
        deleteBtn = view.findViewById(R.id.buttonDeleteSerie);
    }

    @Override
    public void initAccessibility() {
        String reps = getContext().getString(R.string.talkback_reps_edit_text);
        editTxtReps.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(reps + editTxtReps.getText().toString()));
        String kg = getContext().getString(R.string.talkback_kg_edit_text);
        editTxtKg.setAccessibilityDelegate(AccessibilityUtils.createAccesibilityDelegate(kg + editTxtKg.getText().toString()));
    }
    private void printSerie(SerieModel serie){
        String kgToString = Double.toString(serie.getKg());
        String repsToString = Integer.toString(serie.getReps());
        editTxtKg.setText(kgToString);
        editTxtReps.setText(repsToString);
    }
    private void setClickListener(SerieEditableListener serieEditableListener, int position){
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                serieEditableListener.onClickDelete(position);
            }
        });
    }
}
