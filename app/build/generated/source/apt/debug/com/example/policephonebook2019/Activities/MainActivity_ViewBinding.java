// Generated code from Butter Knife. Do not modify!
package com.example.policephonebook2019.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.policephonebook2019.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230807;

  private View view2131230809;

  private View view2131230774;

  private View view2131230758;

  private View view2131230759;

  private View view2131230757;

  private View view2131230756;

  private View view2131230755;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.imgNoti, "method 'onImgNotiClicked'");
    view2131230807 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgNotiClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgUser, "method 'onImgUserClicked'");
    view2131230809 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgUserClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.container, "method 'onContainerClicked'");
    view2131230774 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onContainerClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_phone_list, "method 'onBtnMenuPhoneListClicked'");
    view2131230758 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuPhoneListClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_search, "method 'onBtnMenuSearchClicked'");
    view2131230759 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuSearchClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_map, "method 'onBtnMenuMapClicked'");
    view2131230757 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuMapClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_favorite, "method 'onBtnMenuFavoriteClicked'");
    view2131230756 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuFavoriteClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_contact_us, "method 'onBtnMenuContactUsClicked'");
    view2131230755 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuContactUsClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131230807.setOnClickListener(null);
    view2131230807 = null;
    view2131230809.setOnClickListener(null);
    view2131230809 = null;
    view2131230774.setOnClickListener(null);
    view2131230774 = null;
    view2131230758.setOnClickListener(null);
    view2131230758 = null;
    view2131230759.setOnClickListener(null);
    view2131230759 = null;
    view2131230757.setOnClickListener(null);
    view2131230757 = null;
    view2131230756.setOnClickListener(null);
    view2131230756 = null;
    view2131230755.setOnClickListener(null);
    view2131230755 = null;
  }
}
