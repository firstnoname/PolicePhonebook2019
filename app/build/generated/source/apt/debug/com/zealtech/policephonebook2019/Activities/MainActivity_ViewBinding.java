// Generated code from Butter Knife. Do not modify!
package com.zealtech.policephonebook2019.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.policephonebook2019.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131296377;

  private View view2131296380;

  private View view2131296319;

  private View view2131296300;

  private View view2131296301;

  private View view2131296299;

  private View view2131296298;

  private View view2131296297;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.imgPhoneList = Utils.findRequiredViewAsType(source, R.id.img_phone_list, "field 'imgPhoneList'", ImageView.class);
    target.imgSearch = Utils.findRequiredViewAsType(source, R.id.img_search, "field 'imgSearch'", ImageView.class);
    target.imgMap = Utils.findRequiredViewAsType(source, R.id.img_map, "field 'imgMap'", ImageView.class);
    target.imgFavorite = Utils.findRequiredViewAsType(source, R.id.img_favorite, "field 'imgFavorite'", ImageView.class);
    target.imgContactUs = Utils.findRequiredViewAsType(source, R.id.img_contact_us, "field 'imgContactUs'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.imgNoti, "method 'onImgNotiClicked'");
    view2131296377 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgNotiClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgUser, "method 'onImgUserClicked'");
    view2131296380 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgUserClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.container, "method 'onContainerClicked'");
    view2131296319 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onContainerClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_phone_list, "method 'onBtnMenuPhoneListClicked'");
    view2131296300 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuPhoneListClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_search, "method 'onBtnMenuSearchClicked'");
    view2131296301 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuSearchClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_map, "method 'onBtnMenuMapClicked'");
    view2131296299 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuMapClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_favorite, "method 'onBtnMenuFavoriteClicked'");
    view2131296298 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuFavoriteClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_contact_us, "method 'onBtnMenuContactUsClicked'");
    view2131296297 = view;
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
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgPhoneList = null;
    target.imgSearch = null;
    target.imgMap = null;
    target.imgFavorite = null;
    target.imgContactUs = null;

    view2131296377.setOnClickListener(null);
    view2131296377 = null;
    view2131296380.setOnClickListener(null);
    view2131296380 = null;
    view2131296319.setOnClickListener(null);
    view2131296319 = null;
    view2131296300.setOnClickListener(null);
    view2131296300 = null;
    view2131296301.setOnClickListener(null);
    view2131296301 = null;
    view2131296299.setOnClickListener(null);
    view2131296299 = null;
    view2131296298.setOnClickListener(null);
    view2131296298 = null;
    view2131296297.setOnClickListener(null);
    view2131296297 = null;
  }
}
