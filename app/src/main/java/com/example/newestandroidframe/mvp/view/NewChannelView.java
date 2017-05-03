package com.example.newestandroidframe.mvp.view;

import com.example.newestandroidframe.mvp.entity.NewItemBean;
import com.example.newestandroidframe.mvp.view.baseview.BaseView;

import java.util.List;

/**
 * Created by zhangyb on 2017/4/28.
 */

public interface NewChannelView extends BaseView{

   void initRecycleView(List<NewItemBean> newItemBeenList,List<NewItemBean> newItemBeanListMore);
}
