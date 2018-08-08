// Generated code from Butter Knife. Do not modify!
package com.example.ngothi.feebbackquality;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131165267;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_chup_anh, "field 'mImageButtonChupAnh' and method 'launchCamera'");
    target.mImageButtonChupAnh = Utils.castView(view, R.id.btn_chup_anh, "field 'mImageButtonChupAnh'", ImageButton.class);
    view2131165267 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.launchCamera();
      }
    });
    target.mButtonLap = Utils.findRequiredViewAsType(source, R.id.btn_lap, "field 'mButtonLap'", Button.class);
    target.mButtonGui = Utils.findRequiredViewAsType(source, R.id.btn_gui, "field 'mButtonGui'", Button.class);
    target.mButtonThuHoi = Utils.findRequiredViewAsType(source, R.id.btn_thu_hoi, "field 'mButtonThuHoi'", Button.class);
    target.mButtonChonLoi = Utils.findRequiredViewAsType(source, R.id.btn_chon_loi, "field 'mButtonChonLoi'", Button.class);
    target.mButtonPositionT1 = Utils.findRequiredViewAsType(source, R.id.btn_position_t1, "field 'mButtonPositionT1'", Button.class);
    target.mButtonPositionT2 = Utils.findRequiredViewAsType(source, R.id.btn_position_t2, "field 'mButtonPositionT2'", Button.class);
    target.mButtonPositionF1 = Utils.findRequiredViewAsType(source, R.id.btn_position_f1, "field 'mButtonPositionF1'", Button.class);
    target.mButtonPositionF2 = Utils.findRequiredViewAsType(source, R.id.btn_position_f2, "field 'mButtonPositionF2'", Button.class);
    target.mButtonPositionC1 = Utils.findRequiredViewAsType(source, R.id.btn_position_c1, "field 'mButtonPositionC1'", Button.class);
    target.mButtonPositionC2 = Utils.findRequiredViewAsType(source, R.id.btn_position_c2, "field 'mButtonPositionC2'", Button.class);
    target.mImageViewLogo = Utils.findRequiredViewAsType(source, R.id.image_logo, "field 'mImageViewLogo'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImageButtonChupAnh = null;
    target.mButtonLap = null;
    target.mButtonGui = null;
    target.mButtonThuHoi = null;
    target.mButtonChonLoi = null;
    target.mButtonPositionT1 = null;
    target.mButtonPositionT2 = null;
    target.mButtonPositionF1 = null;
    target.mButtonPositionF2 = null;
    target.mButtonPositionC1 = null;
    target.mButtonPositionC2 = null;
    target.mImageViewLogo = null;

    view2131165267.setOnClickListener(null);
    view2131165267 = null;
  }
}
