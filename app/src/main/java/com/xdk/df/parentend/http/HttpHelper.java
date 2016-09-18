package com.xdk.df.parentend.http;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xdk.df.parentend.MainActivity;
import com.xdk.df.parentend.R;
import com.xdk.df.parentend.data.CheckDetail;
import com.xdk.df.parentend.data.CurrentUser;
import com.xdk.df.parentend.data.HttpData;
import com.xdk.df.parentend.data.ResultMark;
import com.xdk.df.parentend.http.okhttp.OkHttpUtils;
import com.xdk.df.parentend.http.okhttp.callback.Callback;
import com.xdk.df.parentend.http.wait.DialogPolicy;
import com.xdk.df.parentend.ui.login.LoginActivity;
import com.xdk.df.parentend.utils.BitmapCompressor;
import com.xdk.df.parentend.utils.FileUtils;
import com.xdk.df.parentend.utils.ImageDownLoader;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ThreadPoolUtils;
import com.xdk.df.parentend.utils.ToastUtils;

import org.json.JSONObject;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by Administrator on 2016/8/10.
 */
public class HttpHelper {
    private final static String UserName = "sa";//用户名
    private final static String Password = "cinzn2055";//密码

    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Connection getConnectSQl(final Activity activity, Handler handler) {
        try { // 加载驱动程序
            Connection con = null;
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:jtds:sqlserver://120.24.227.222:2055/czn_syktwww ", UserName,
                    Password);
            return con;
        } catch (ClassNotFoundException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(activity, "加载驱动程序出错");
                }
            });
        } catch (final SQLException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(activity, e.getMessage().toString());
                }
            });
        } catch (final Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(activity, "未知异常" + e.getMessage().toString());
                }
            });
        }
        return null;
    }

    public static void userLogin(final Activity activity, final Handler loginHnadler, final String schoolCode, final String username, final String password) {
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return;
        }
        final DialogPolicy waitPolicy = new DialogPolicy(activity);
        waitPolicy.displayLoading();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, loginHnadler);
                if (con == null) {
                    waitPolicy.disappear();
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    String sql = "SELECT * FROM AppUser where schoolcode = '" + schoolCode + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (!rs.next()) {
                        loginHnadler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShort(activity, R.string.str_login_wrong_school_code);
                            }
                        });
                    } else {
                        String sql2 = "SELECT * FROM AppUser where schoolcode = '" + schoolCode + "' and accountid = '" + username + "'";
                        rs = stmt.executeQuery(sql2);
                        if (!rs.next()) {
                            loginHnadler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showShort(activity, R.string.str_login_studentCode_notExist);
                                }
                            });
                        } else {
                            String sql3 = "SELECT * FROM AppUser where schoolcode = '" + schoolCode + "' and accountid = '" + username + "' and userpwd = '" + password + "'";
                            rs = stmt.executeQuery(sql3);
                            if (!rs.next()) {
                                loginHnadler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showShort(activity, R.string.str_login_password_wrong);
                                    }
                                });
                            } else {
                                int vip = compare_date(rs.getString("validdate"), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                if (vip == -1) {
                                    loginHnadler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showShort(activity, R.string.str_user_no_vip);
                                        }
                                    });
                                    return;
                                }

                                CurrentUser user = new CurrentUser();
                                user.setAutoid(rs.getInt("autoid"));
                                user.setAccountid(rs.getString("accountid"));
                                user.setApertment(rs.getString("apartment"));
                                user.setUserstatus(rs.getString("userstatus"));
                                user.setUserstate(rs.getString("userstate"));
                                user.setLoginnumber(rs.getInt("loginnumber"));
                                user.setLogintime(rs.getString("logintime"));
                                user.setCardmoney(rs.getDouble("cardmoney"));
                                user.setCardstate(rs.getString("cardstate"));
                                user.setGrade(rs.getInt("grade"));
                                user.setName(rs.getString("name"));
                                user.setProfessional(rs.getString("professional"));
                                user.setRoom(rs.getString("room"));
                                user.setValiddate(rs.getString("validdate"));
                                user.setUserpwd(rs.getString("userpwd"));
                                user.setTelephone3(rs.getString("telephone3"));
                                user.setTelephone2(rs.getString("telephone2"));
                                user.setTelephone1(rs.getString("telephone1"));
                                user.setStudentid(rs.getString("studentid"));
                                user.setSchool(rs.getString("school"));
                                user.setSchoolcode(rs.getString("schoolcode"));
                                user.setSclass(rs.getInt("class"));
                                user.setUserpwd(rs.getString("userpwd"));
                                if (user.getUserstatus().equals("教师")) {
                                    loginHnadler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showShort(activity, "请登录教师端");
                                        }
                                    });
                                    return;
                                }
                                loginHnadler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showShort(activity, R.string.str_login_success);
                                    }
                                });
                                SharedPreferenceHelper.putCurrentUser(activity, user);
                                SharedPreferenceHelper.putLoginTime(activity, System.currentTimeMillis());
                                ((LoginActivity) activity).goActivity(MainActivity.class);
                                activity.finish();
                            }
                        }
                    }
                    rs.close();
                    stmt.close();
                } catch (final SQLException e) {
                    loginHnadler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                    e.printStackTrace();
                } finally {
                    waitPolicy.disappear();
                    if (con != null)
                        try {
                            con.close();
                        } catch (SQLException e) {
                        }
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
    }

    public static Bitmap getCurrentUserPhoto(final Activity activity, final Handler handler, final ImageView imageView) {
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return null;
        }
        final DialogPolicy waitPolicy = new DialogPolicy(activity);
        waitPolicy.displayLoading();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, handler);
                if (con == null) {
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    String sql = "SELECT * FROM UserPicture where picid = '" + SharedPreferenceHelper.getCurrentUser(activity).getAccountid() + "'and schoolcode = '" + SharedPreferenceHelper.getCurrentUser(activity).getSchoolcode() + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        byte[] data = blob2ByteArr(rs.getBlob("picture"));
                        Message message = new Message();
                        message.obj = data;
                        ImageDownLoader loader = new ImageDownLoader(activity);
                        loader.addBitmapToMemoryCache(sql, BitmapFactory.decodeByteArray(data, 0, data.length));
                        FileUtils utils = new FileUtils(activity);
                        if (!utils.isFileExists(sql)) {
                            utils.savaBitmap(sql, BitmapFactory.decodeByteArray(data, 0, data.length));
                        }
                        handler.sendMessage(message);
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShort(activity, R.string.str_main_top_student_no_photo);
                            }
                        });
                    }
                    rs.close();
                    stmt.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    waitPolicy.disappear();
                    if (con != null)
                        try {
                            con.close();
                        } catch (SQLException e) {
                        }
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
        return null;
    }


    public static void getCheckDetail(final Activity activity, final Handler loginHnadler) {
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return;
        }
        final DialogPolicy waitPolicy = new DialogPolicy(activity);
        waitPolicy.displayLoading();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, loginHnadler);
                if (con == null) {
                    waitPolicy.disappear();
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    String sql = "SELECT * FROM CheckDetail where accountid = '" + SharedPreferenceHelper.getCurrentUser(activity).getAccountid() + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    boolean flag = false;
                    List<CheckDetail> list = new ArrayList<CheckDetail>();
                    while (rs.next()) {
                        flag = true;
                        CheckDetail checkDetail = new CheckDetail();
                        checkDetail.setAutoid(rs.getInt("autoid"));
                        checkDetail.setAccountid(rs.getString("accountid"));
                        checkDetail.setCheckaspect(rs.getString("checkaspect"));
                        checkDetail.setHappentime(rs.getString("happentime"));
                        checkDetail.setChecktype(rs.getString("checktype"));
                        checkDetail.setHappenaddress(rs.getString("happenaddress"));
                        checkDetail.setHappendate(rs.getString("happendate"));
                        list.add(checkDetail);
                    }
                    Message message = new Message();
                    if (flag) {
                        message.obj = list;
                    } else {
                        message.what = 1;
                    }
                    loginHnadler.sendMessage(message);
                    rs.close();
                    stmt.close();
                } catch (final SQLException e) {
                    loginHnadler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                    e.printStackTrace();
                } finally {
                    waitPolicy.disappear();
                    if (con != null)
                        try {
                            con.close();
                        } catch (SQLException e) {
                        }
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
    }

    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static byte[] blob2ByteArr(Blob blob) throws Exception {
        byte[] b = null;
        try {
            if (blob != null) {
                long in = 1;
                b = blob.getBytes(in, (int) (blob.length()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("fault");
        }

        return b;
    }

    public static void getDada(final Activity activity, final Handler loginHnadler, final String sql) {
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return;
        }
        final DialogPolicy waitPolicy = new DialogPolicy(activity);
        waitPolicy.displayLoading();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, loginHnadler);
                if (con == null) {
                    waitPolicy.disappear();
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    HttpData data = new HttpData();
                    Message message = new Message();
                    if (rs != null) {
                        data.setConnection(con);
                        data.setResultSet(rs);
                        data.setStatement(stmt);
                        message.obj = data;
                        loginHnadler.sendMessage(message);
                    } else {
                        message.what = 1;
                    }
                } catch (final SQLException e) {
                    loginHnadler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                    e.printStackTrace();
                } finally {
                    waitPolicy.disappear();
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
    }

    public interface PortraitCallback {
        void onResponse(byte[] bytes);
    }

    public static void getCheckPortrait(final Activity activity, final Handler loginHnadler, final String sql, final PortraitCallback callback, final String currentId) {
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return;
        }
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, loginHnadler);
                if (con == null) {
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        rs.getBlob("picture");
                        final byte[] data = blob2ByteArr(rs.getBlob("picture"));
                        Bitmap bitmap = BitmapCompressor.comp(BitmapFactory.decodeByteArray(data, 0, data.length));
                        FileUtils fileUtils = new FileUtils(activity);
                        fileUtils.savaBitmap(currentId, bitmap);
                        ImageDownLoader loader = new ImageDownLoader(activity);
                        loader.addBitmapToMemoryCache(currentId, bitmap);
                        if (data != null) {
                            loginHnadler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(data);
                                }
                            });
                        } else {
                            loginHnadler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showShort(activity, "没有抓拍的图片");
                                }
                            });
                        }
                    }
                    rs.close();
                    stmt.close();
                } catch (final SQLException e) {
                    loginHnadler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
    }

    public static void changeDada(final Activity activity, final Handler loginHnadler, final String sql) {
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return;
        }
        final DialogPolicy waitPolicy = new DialogPolicy(activity);
        waitPolicy.displayLoading();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, loginHnadler);
                if (con == null) {
                    waitPolicy.disappear();
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    int a = stmt.executeUpdate(sql);
                    Message message = new Message();
                    message.what = a;
                    loginHnadler.sendMessage(message);
                    stmt.close();
                } catch (final SQLException e) {
                    loginHnadler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                    e.printStackTrace();
                } finally {
                    waitPolicy.disappear();
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
    }

    public static void getPicture(final Activity activity, final Handler handler, final String sql, final HttpPictureCallBack callback, final String currentId) {
        ImageDownLoader loader = new ImageDownLoader(activity);
        if (loader.showCacheBitmap(currentId) != null) {
            callback.onResponse(loader.showCacheBitmap(currentId));
            return;
        }
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return;
        }
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, handler);
                if (con == null) {
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        final byte[] data = blob2ByteArr(rs.getBlob("picture"));
                        final Bitmap bitmap = BitmapCompressor.comp(BitmapFactory.decodeByteArray(data, 0, data.length));
                        FileUtils fileUtils = new FileUtils(activity);
                        fileUtils.savaBitmap(currentId, bitmap);
                        ImageDownLoader loader = new ImageDownLoader(activity);
                        loader.addBitmapToMemoryCache(currentId, bitmap);
                        if (data != null) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onResponse(bitmap);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showShort(activity, "没有抓拍的图片");
                                }
                            });
                        }
                    }
                    rs.close();
                    stmt.close();
                } catch (final SQLException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
    }

    public static void getResult(final Activity activity, final Handler handler, final String sql, final HttpCallBack callBack) {
        if (!isNetworkAvailable(activity)) {
            ToastUtils.showShort(activity, R.string.str_no_net);
            return;
        }
        final DialogPolicy waitPolicy = new DialogPolicy(activity);
        waitPolicy.displayLoading();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Connection con = getConnectSQl(activity, handler);
                if (con == null) {
                    waitPolicy.disappear();
                    return;
                }
                try {
                    Statement stmt = con.createStatement();
                    final ResultSet rs = stmt.executeQuery(sql);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callBack.onResponse(rs);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    rs.close();
                    stmt.close();
                } catch (final SQLException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(activity, e.getMessage());
                        }
                    });
                    e.printStackTrace();
                } finally {
                    waitPolicy.disappear();
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        ThreadPoolUtils.execute(runnable);
    }

    public interface HttpCallBack {
        void onResponse(ResultSet rs) throws SQLException;
    }

    public interface HttpPictureCallBack {
        void onResponse(Bitmap bitmap);
    }

    public static void getOkhttp(Activity activity, String url, Callback callback) {
        OkHttpUtils.post().url(url).tag(activity).build().execute(callback);
    }

    public static void postOkhttp(Activity activity, String url, Class content, Callback callback) {
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(content))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .tag(activity)
                .build()
                .execute(callback);
    }
}
