package cn.weeon.job.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ClassName:   BaseFragment
 * Reason:	 TODO ADD REASON.
 * Date:    2014/10/20
 *
 * @author liying
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment {

    protected View view;
    protected LayoutInflater inflater;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(getLayout(), null);
        init();
        return view;
    }

    /**
     *
     * getLayout:(获得布局文件). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     *
     * @author liying
     * @return
     * @since JDK 1.6
     */
    public abstract int getLayout();

    /**
     *
     * init:(初始化方法). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author liying
     * @since JDK 1.6
     */
    public abstract void init();
}
