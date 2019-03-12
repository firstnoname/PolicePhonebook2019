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

  private View view2131296363;

  private View view2131296365;

  private View view2131296313;

  private View view2131296297;

  private View view2131296298;

  private View view2131296296;

  private View view2131296295;

  private View view2131296294;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.imgNoti, "method 'onImgNotiClicked'");
    view2131296363 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgNotiClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgUser, "method 'onImgUserClicked'");
    view2131296365 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgUserClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.container, "method 'onContainerClicked'");
    view2131296313 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onContainerClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_phone_list, "method 'onBtnMenuPhoneListClicked'");
    view2131296297 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuPhoneListClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_search, "method 'onBtnMenuSearchClicked'");
    view2131296298 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuSearchClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_map, "method 'onBtnMenuMapClicked'");
    view2131296296 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuMapClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_favorite, "method 'onBtnMenuFavoriteClicked'");
    view2131296295 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuFavoriteClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_contact_us, "method 'onBtnMenuContactUsClicked'");
    view2131296294 = view;
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


    view2131296363.setOnClickListener(null);
    view2131296363 = null;
    view2131296365.setOnClickListener(null);
    view2131296365 = null;
    view2131296313.setOnClickListener(null);
    view2131296313 = null;
    view2131296297.setOnClickListener(null);
    view2131296297 = null;
    view2131296298.setOnClickListener(null);
    view2131296298 = null;
    view2131296296.setOnClickListener(null);
    view2131296296 = null;
    view2131296295.setOnClickListener(null);
    view2131296295 = null;
    view2131296294.setOnClickListener(null);
    view2131296294 = null;
  }
}
