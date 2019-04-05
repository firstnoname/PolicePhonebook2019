// Generated code from Butter Knife. Do not modify!
package com.zealtech.policephonebook2019.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.policephonebook2019.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131361924;

  private View view2131361921;

  private View view2131361861;

  private View view2131361839;

  private View view2131361840;

  private View view2131361838;

  private View view2131361837;

  private View view2131361836;

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
    target.tvActionbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_actionbar, "field 'tvActionbarTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.imgUser, "field 'imgProfile' and method 'onImgUserClicked'");
    target.imgProfile = Utils.castView(view, R.id.imgUser, "field 'imgProfile'", ImageView.class);
    view2131361924 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgUserClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgNoti, "method 'onImgNotiClicked'");
    view2131361921 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImgNotiClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.container, "method 'onContainerClicked'");
    view2131361861 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onContainerClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_phone_list, "method 'onBtnMenuPhoneListClicked'");
    view2131361839 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuPhoneListClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_search, "method 'onBtnMenuSearchClicked'");
    view2131361840 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuSearchClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_map, "method 'onBtnMenuMapClicked'");
    view2131361838 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuMapClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_favorite, "method 'onBtnMenuFavoriteClicked'");
    view2131361837 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMenuFavoriteClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_menu_contact_us, "method 'onBtnMenuContactUsClicked'");
    view2131361836 = view;
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
    target.tvActionbarTitle = null;
    target.imgProfile = null;

    view2131361924.setOnClickListener(null);
    view2131361924 = null;
    view2131361921.setOnClickListener(null);
    view2131361921 = null;
    view2131361861.setOnClickListener(null);
    view2131361861 = null;
    view2131361839.setOnClickListener(null);
    view2131361839 = null;
    view2131361840.setOnClickListener(null);
    view2131361840 = null;
    view2131361838.setOnClickListener(null);
    view2131361838 = null;
    view2131361837.setOnClickListener(null);
    view2131361837 = null;
    view2131361836.setOnClickListener(null);
    view2131361836 = null;
  }
}
