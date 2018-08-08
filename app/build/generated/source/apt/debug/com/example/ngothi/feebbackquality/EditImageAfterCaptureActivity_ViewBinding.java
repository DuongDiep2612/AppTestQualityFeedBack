// Generated code from Butter Knife. Do not modify!
package com.example.ngothi.feebbackquality;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditImageAfterCaptureActivity_ViewBinding implements Unbinder {
  private EditImageAfterCaptureActivity target;

  private View view2131165276;

  private View view2131165234;

  @UiThread
  public EditImageAfterCaptureActivity_ViewBinding(EditImageAfterCaptureActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EditImageAfterCaptureActivity_ViewBinding(final EditImageAfterCaptureActivity target,
      View source) {
    this.target = target;

    View view;
    target.mMyViewImage = Utils.findRequiredViewAsType(source, R.id.my_view, "field 'mMyViewImage'", MyView.class);
    view = Utils.findRequiredView(source, R.id.btn_save_image, "method 'saveImage'");
    view2131165276 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.saveImage();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnEditPhoto, "method 'editImage'");
    view2131165234 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.editImage();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EditImageAfterCaptureActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mMyViewImage = null;

    view2131165276.setOnClickListener(null);
    view2131165276 = null;
    view2131165234.setOnClickListener(null);
    view2131165234 = null;
  }
}
