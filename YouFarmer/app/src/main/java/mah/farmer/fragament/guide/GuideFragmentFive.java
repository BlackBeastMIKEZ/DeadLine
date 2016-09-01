package mah.farmer.fragament.guide;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.locTest.R;

/**引导页面五
 * Created by 黑色野兽迈特祖 on 2016/5/2.
 */
public class GuideFragmentFive extends Fragment {

    private Context ctx;


    public GuideFragmentFive(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        view = View.inflate(ctx, R.layout.fragment_guidefive, null);

        return view;
    }
}
