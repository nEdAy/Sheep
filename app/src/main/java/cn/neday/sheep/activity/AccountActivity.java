// TakePhoto.TakeResultListener, InvokeListener {
//    private SimpleDraweeView riv_avatar;
//    private TextView tv_nickname_real, tv_account_real;
//    private CatLoadingView catLoadingView;
//    private SharedPreferencesHelper sharedPreferencesHelper;
//    private TakePhoto takePhoto;
//    private InvokeParam invokeParam;

//    @Override
//    public void initView(Bundle savedInstanceState) {
//        getTakePhoto().onCreate(savedInstanceState);

//        riv_avatar = findViewById(R.id.riv_avatar);
//        tv_nickname_real = findViewById(R.id.tv_nickname_real);
//        tv_account_real = findViewById(R.id.tv_account_real);
//        findViewById(R.id.rl_avatar).setOnClickListener(v -> showActionSheet());
//        findViewById(R.id.rl_nickname).setOnClickListener(v -> getOperation().startActivity(UpdateInfoActivity.class));
//        findViewById(R.id.rl_key_login).setOnClickListener(v -> getOperation().startActivity(UpdateNewPasswordActivity.class));
//        findViewById(R.id.rl_change_skin).setOnClickListener(v -> getOperation().startActivity(ChangeSkinActivity.class));
//        findViewById(R.id.rl_setting_center).setOnClickListener(v -> getOperation().startActivity(PushSettingActivity.class));

//        ImageView iv_open_province_flow = findViewById(R.id.iv_open_province_flow);
//        ImageView iv_close_province_flow = findViewById(R.id.iv_close_province_flow);

//        // 初始化省流模式按钮状态
//        if (sharedPreferencesHelper.isAllowProvinceFlowModel()) {
//            iv_open_province_flow.setVisibility(View.VISIBLE);
//            iv_close_province_flow.setVisibility(View.INVISIBLE);
//        } else {
//            iv_open_province_flow.setVisibility(View.INVISIBLE);
//            iv_close_province_flow.setVisibility(View.VISIBLE);
//        }
//        //省流模式切换
//        findViewById(R.id.rl_province_flow).setOnClickListener(v -> {
//            if (iv_open_province_flow.getVisibility() == View.VISIBLE) {
//                iv_open_province_flow.setVisibility(View.INVISIBLE);
//                iv_close_province_flow.setVisibility(View.VISIBLE);
//                sharedPreferencesHelper.setAllowProvinceFlowModelEnable(false);
//            } else {
//                iv_open_province_flow.setVisibility(View.VISIBLE);
//                iv_close_province_flow.setVisibility(View.INVISIBLE);
//                sharedPreferencesHelper.setAllowProvinceFlowModelEnable(true);
//            }
//        });
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        getTakePhoto().onSaveInstanceState(outState);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        getTakePhoto().onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onResumeAfter() {
//        currentUser = User.getCurrentUser();
//        if (!CommonUtils.isNetworkAvailable()) {
//            CommonUtils.showToast(R.string.network_tips);
//            return;
//        }
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//                        .getUser(currentUser.getObjectId()),
//                () ->
//                        catLoadingView.show(getSupportFragmentManager(), TAG),
//                user -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    refreshUser(user);
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast(getString(R.string.get_user_info_error));
//                    Logger.e(throwable.getMessage());
//                });
//    }
//


//    /**
//     * 刷新用户信息显示
//     *
//     * @param user 刷新的用户数据
//     */
//    private void refreshUser(User user) {
//        refreshAvatar(user.getAvatar());
//        String nickname = user.getNickname();
//        if (TextUtils.isEmpty(nickname) || nickname.equals(getString(R.string.default_nickname))) {
//            tv_nickname_real.setText(getString(R.string.default_nickname));
//        } else {
//            tv_nickname_real.setText(nickname);
//        }
//        String account = user.getUsername();
//        tv_account_real.setText(account);
//    }
//
//
//    /**
//     * 显示获取头像选择菜单
//     */
//    private void showActionSheet() {
//        final String[] stringItems = {getString(R.string.pick_from_capture), getString(R.string.pick_from_gallery)};
//        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, null);
//        dialog.isTitleShow(false).layoutAnimation(null).show();
//        dialog.setOnOperItemClickL((parent, view, position, id) -> {
//            File file = new File(Environment.getExternalStorageDirectory(), "/kdkb/cache/" + System.currentTimeMillis() + ".jpg");
//            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//            Uri imageUri = Uri.fromFile(file);
//            configCompress(takePhoto);
//            switch (position) {
//                case 0:
//                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
//                    break;
//                case 1:
//                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
//                    break;
//                default:
//                    break;
//            }
//            dialog.dismiss();
//        });
//    }
//
//    /**
//     * 配置裁剪参数
//     */
//    private CropOptions getCropOptions() {
//        CropOptions.Builder builder = new CropOptions.Builder();
//        builder.setAspectX(400).setAspectY(400);
//        builder.setWithOwnCrop(false);
//        return builder.create();
//    }
//
//
//    /**
//     * 配置压缩参数
//     *
//     * @param takePhoto takePhoto实例
//     */
//    private void configCompress(TakePhoto takePhoto) {
//        CompressConfig config = new CompressConfig.Builder().setMaxPixel(102400).setMaxPixel(400).create();
//        takePhoto.onEnableCompress(config, true);
//    }
//
//    /**
//     * 更新用户头像url
//     *
//     * @param url 需要绑定用户头像的url
//     */
//    private void updateUserAvatar(String url) {
//        User user = new User();
//        user.setAvatar(url);
//        toSubscribe(RxFactory.getUserServiceInstance(currentUser.getSessionToken())
//                        .updateUser(currentUser.getObjectId(), user),
//                baseObject -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    refreshAvatar(user.getAvatar());
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast(getString(R.string.refresh_error));
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//    /**
//     * 更新头像 refreshAvatar
//     */
//    private void refreshAvatar(String avatar) {
//        if (avatar != null && !avatar.equals("")) {
//            Uri uri = Uri.parse(avatar);
//            riv_avatar.setImageURI(uri);
//        } else {
//            riv_avatar.setImageResource(R.drawable.avatar_default);
//        }
//    }
//
//    /**
//     * 接受事件后刷新昵称
//     *
//     * @param nickname 昵称字符串
//     */
//    @Subscribe(
//            thread = EventThread.MAIN_THREAD,
//            tags = {
//                    @Tag(StaticConfig.ACTION_UPDATE_NICKNAME_SUCCESS_FINISH)
//            }
//    )
//    public void refreshNickname(String nickname) {
//        tv_nickname_real.setText(nickname);
//        tv_nickname_real.setEnabled(false);
//    }
//

//
//    /**
//     * 获取TakePhoto实例
//     */
//    private TakePhoto getTakePhoto() {
//        if (takePhoto == null) {
//            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
//        }
//        return takePhoto;
//    }
//
//
//    @Override
//    public void takeSuccess(TResult result) {
//        // 上传头像
//        File file = new File(result.getImage().getPath());
//        // 创建 RequestBody，用于封装 请求RequestBody
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//        toSubscribe(RxFactory.getPublicServiceInstance(null)
//                        .uploadImage(file.getName(), requestFile),
//                () ->
//                        catLoadingView.show(getSupportFragmentManager(), TAG),
//                uploadJSON -> {
//                    // 更新BmobUser对象
//                    updateUserAvatar(uploadJSON.getUrl());
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("更新失败");
//                    Logger.e(throwable.getMessage());
//                });
//        Logger.i("takeSuccess：" + result.getImage().getPath());
//    }
//
//    @Override
//    public void takeFail(TResult result, String msg) {
//        CommonUtils.showToast("获取头像失败");
//        Logger.i(msg);
//    }
//
//    @Override
//    public void takeCancel() {
//        Logger.i("取消操作");
//    }
//
//    @Override
//    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
//        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
//        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
//            this.invokeParam = invokeParam;
//        }
//        return type;
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //以下代码为处理Android6.0、7.0动态权限所需
//        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
//    }
//}
