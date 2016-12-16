import com.nostra13.universalimageloader.core.ImageLoader;

public class Constants {

    public final static int           PAGE_SIZE                              = 20;

    public final static String        SERVER_URL                             = "http://123.56.138.147";

    public final static int           MSG_HTTP_EXCEPTION                     = 99;

    public final static int           START_TO_ACCESS                        = -1;                                                     // 开始访问请求

    public final static int           FAIL_TO_ACCESS                         = 0;                                                      // 访问结果

    public final static int           SUCCESS_TO_ACCESS                      = 1;

    public final static String        FILENAME                               = "zuibangai";                                            // 存储配置信息 的xml文件 名
    public final static String        MUSIC_ENABLE_KEY                       = "music_enable";
    public final static String        SOUND_EFECCT_ENABLE_KEY                = "sound_effect_enable";
    public final static String        UPDATE_RECORD_KEY                      = "update_record";
    public final static String        MOBILE_AD_CLICK_REWARD_KEY             = "MobileAdClickReward";
    public final static String        MOBILE_SNS_SHARE_REWARD_KEY            = "MobileSNSShareReward";
    public final static String        DESCRIPTOR                             = "www.zuibon.com";

    // ~~~~~~~~~~~~~~~~~华丽分割线（固定文件地址）~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public final static String        TAKE_SHOT_SOUND_FILE                   = "file:///system/media/audio/ui/camera_click.ogg";

    public static final String        DATE_FORMAT_NORMAL                     = "yyyy-MM-dd HH:mm:ss";
    public static final String        DATE_FORMAT_FOR_ID                     = "yyyyMMddHHmmssSS";
    public static final String        DATE_FORMAT_FRO_CHAT                   = "MM-dd HH:mm";
    // public static final int Type_StaticShow = 0; //图片类型
    // public static final int Type_SpriteWalk = 1;

    public static final int           LOAD_ONE                               = 101;                                                    // imageloader 下载完一张图片

    public static final int           LOAD_ALL_FOR_SHOP_ACTIVITY             = 102;                                                    // imageloader
                                                                                                                                        // 下载完所有图片

    public static final int           LOAD_ALL_FOR_US_ACTIVITY               = 103;

    public static final int           LOAD_FAIL                              = 104;                                                    // imageloader 下载失败

    /**
     * 一起的事
     */
    public static final int           TYPE_ADD_MEMORY                        = 21;
    /**
     * 我的愿望
     */
    public static final int           TYPE_MY_DESIRE                         = 22;
    /**
     * 聊天
     */
    public static final int           TYPE_CHAT                              = 23;
    /**
     * 给Ta负分
     */
    public static final int           TYPE_NEGATIVE                          = 24;
    /**
     * 我的付出
     */
    public static final int           TYPE_MY_EXPEND                         = 25;
    /**
     * // * Ta的付出
     */
    public static final int           TYPE_HER_EXPEND                        = 26;
    /**
     * Ta的愿望
     */
    public static final int           TYPE_HER_DESIRE                        = 27;
    /**
     * 最近爱情记录
     */
    public static final int           TYPE_RECENT_LOVE_RECORD                = 30;
    /**
     * 最近愿望记录
     */
    public static final int           TYPE_RECENT_WISH_RECORD                = 31;
    /**
     * 更新最近记录的消息指
     */
    public static final int           UPDATE_RECENT_RECORD                   = 32;
    /**
     * 提示没有绑定的类型
     */
    public static final int           TYPE_NO_BINDING                        = 33;
    /**
     * 提示愿望分不够的类型
     */
    public static final int           TYPE_NO_SCORE                          = 34;
    /**
     * 更新未读聊天消息数目
     */
    public static final int           TYPE_UPDATE_UNREAD_CHAT_MESSAGES_COUNT = 35;
    public static final long          TIP_SHOW_TIME                          = 3000;
    public static ImageLoader imageLoader                            = ImageLoader
                                                                                 .getInstance();
//    public static DisplayImageOptions image_display_options                  = new DisplayImageOptions.Builder()
//                                                                                 .showImageOnLoading(
//                                                                                     R.drawable.ic_stub)
//                                                                                 .showImageForEmptyUri(
//                                                                                     R.drawable.ic_empty)
//                                                                                 .showImageOnFail(
//                                                                                     R.drawable.ic_error)
//                                                                                 .displayer(
//                                                                                     new RoundedBitmapDisplayer(
//                                                                                         20))
//                                                                                 .cacheInMemory(
//                                                                                     true)
//                                                                                 .cacheOnDisc(true)
//                                                                                 .build();

//    public static final String        forgetPasswordUrl                      = MyApplication.serverIp
//                                                                               + "/security/retrievePassword";
//
//    public static final String        privacyUrl                             = MyApplication.serverIp
//                                                                               + "/privacyPolicy";
    /**
     * 广播action
     */
    public static final String        POP_CHAT_ACTION                        = "com.zuibon.zuibangai.pop_chat_action";
    public static final String        CANCEL_CHAT_ACTION                     = "com.zuibon.zuibangai.cancel_chat_action";
    public static final String        CONFIRM_AWARD_DIALOG_ACTION            = "com.zuibon.zuibangai.confirm_award_dialog_action";
    public static final String        HIDDEN_SIGN_IN_ACTION                  = "com.zuibon.zuibangai.hidden_sign_in_action";
    public static final String        RECEIVE_NEW_CHAT                       = "com.zuibon.zuibangai.receive_new_chat";
    public static final String        RECEIVE_BUY_MSG                        = "com.zuibon.zuibangai.receive_buy_msg";
    public static final String        UPDATE_UNREAD_CHAT_MESSAGE_COUNT       = "com.zuibon.zuibangai.update_unread_chat_message_count";

    /**
     * 聊天消息发送状态
     * 
     * @author hadoop
     * 
     */
    public static enum ChatStatus {
        Sending, Succeed, Failed;
    }

    /**
     * 聊天界面的Handler消息值 重发
     */
    public static final int CHAT_RE_SEND           = 50;
    /**
     * 聊天界面的Handler消息值 刷新ListView
     */
    public static final int CHAT_REFRESH_LIST_VIEW = 51;
}
