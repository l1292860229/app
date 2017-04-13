package com.example.administrator.presenter;

import com.example.administrator.activity.BaseActivity;
import com.example.administrator.entity.City;
import com.example.administrator.entity.Province;
import com.example.administrator.entity.constant.UrlConstants;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.interfaceview.IUEditProfileView;
import com.example.administrator.util.GetDataUtil;
import com.example.administrator.util.GsonUtil;
import com.example.administrator.util.NetworkUtil;
import com.example.administrator.util.StringUtil;
import com.example.administrator.util.UIUtil;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.util.GetDataUtil.getUserInfo;

/**
 * Created by Administrator on 2017/1/25.
 */

public class EditProfilePresenter extends BasePresenter {
    private IUEditProfileView editProfileView;
    public EditProfilePresenter(BaseActivity context, IUEditProfileView editProfileView){
        super(context);
        this.editProfileView = editProfileView;
    }
    public void init(){
       UserInfo userInfo =  getUserInfo(context);
        editProfileView.init(userInfo);
    }
    public void updateUserInfo(UserInfo newUserInfo){
        UserInfo userInfo = getUserInfo(context);
        RequestParams params = new RequestParams();
        if(!StringUtil.isNull(newUserInfo.getHeadsmall())){
            try {
                params.put("pic", new File(newUserInfo.getHeadsmall()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        params.put("uid", userInfo.getUid());
        params.put("nickname", newUserInfo.getNickname());
        params.put("gender", newUserInfo.getGender().ordinal());
        params.put("sign", newUserInfo.getSign());
        params.put("province", newUserInfo.getProvince());
        params.put("city", newUserInfo.getCity());
        params.put("token", userInfo.getToken());
        params.put("companywebsite", newUserInfo.getCompanywebsite());
        params.put("industry", newUserInfo.getIndustry());
        params.put("company", newUserInfo.getCompany());
        params.put("companyaddress", newUserInfo.getCompanyaddress());
        params.put("job", newUserInfo.getJob());
        params.put("provide", newUserInfo.getProvide());
        params.put("demand", newUserInfo.getDemand());
        params.put("telephone", newUserInfo.getTelephone());
        //安全较验
        NetworkUtil.safeDate(params);
        editProfileView.showLoading();
        client.post(UrlConstants.USER_EDIT, params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        editProfileView.hideLoading();
                        String data = new String(arg2);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(data);
                            data = json.getString("data");
                            UserInfo temp = GsonUtil.parseJsonWithGson(data, UserInfo.class);
                            UserInfo userInfo =  GetDataUtil.getUserInfo(context);
                            userInfo.setHeadsmall(temp.getHeadsmall());
                            userInfo.setNickname(temp.getNickname());
                            userInfo.setGender(temp.getGender());
                            userInfo.setProvince(temp.getProvince());
                            userInfo.setCity(temp.getCity());
                            userInfo.setSign(temp.getSign());
                            userInfo.setCompanywebsite(temp.getCompanywebsite());
                            userInfo.setIndustry(temp.getIndustry());
                            userInfo.setCompany(temp.getCompany());
                            userInfo.setCompanyaddress(temp.getCompanyaddress());
                            userInfo.setJob(temp.getJob());
                            userInfo.setProvide(temp.getProvide());
                            userInfo.setDemand(temp.getDemand());
                            userInfo.setTelephone(temp.getTelephone());
                            GetDataUtil.setUserInfo(context,GsonUtil.objectToJson(userInfo));
                            editProfileView.close();
                            UIUtil.showMessage(context,"修改成功");
                        } catch (JSONException e) {
                            UIUtil.showMessage(context,"修改失败");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        editProfileView.hideLoading();
                        UIUtil.showMessage(context,"网络异常");
                    }
                });
    }
    Province[] provinces;
    public List<String> getProvinceList(){
        provinces =  GetDataUtil.getAreaCode(context);
        List<String> provinceList = new ArrayList<>();
        for (Province province : provinces) {
            provinceList.add(province.getState());
        }
        return provinceList;
    }
    public List<String> getCityList(int index){
        if (provinces==null && provinces.length>=index) {
            return new ArrayList<String>();
        }
        List<String> cityList = new ArrayList<>();
        for (City city : provinces[index].getCities()) {
            cityList.add(city.getCity());
        }
        return cityList;
    }
}
