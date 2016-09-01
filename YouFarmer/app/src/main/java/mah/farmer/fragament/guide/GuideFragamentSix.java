package mah.farmer.fragament.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.locTest.R;

import mah.farmer.http.FarmerConfig;
import mah.farmer.ui.FarmerLoginAcivity;


/**引导页面六
 * Created by 黑色野兽迈特祖 on 2016/4/18.
 */
public class GuideFragamentSix extends Fragment {
    private Context ctx;

    public GuideFragamentSix(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = View.inflate(ctx, R.layout.fragament_guidesix, null);
        TextView mBtn = (TextView) view.findViewById(R.id.MyLoginBtn);
        mBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                FarmerConfig.cacheFarmerISfirstRun(getActivity(), false);
                Intent intent = new Intent(ctx, FarmerLoginAcivity.class);
                ctx.startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}
