
//        userId = getIntent().getStringExtra("userId");
//        tv_credits = findViewById(R.id.tv_credits);
//        rl_no_data = findViewById(R.id.rl_no_data);
//        rl_no_network = findViewById(R.id.rl_no_network);
//        mRecyclerView = findViewById(R.id.recycler_view);
//        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.red,
//                R.color.orange, R.color.green,
//                R.color.blue);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        initAdapter();
//        // 主动查询
//        onRefresh();
//    }

//    private void initAdapter() {
//        mQuickAdapter = new CreditsDetailsAdapter(new ArrayList<>());
//        mRecyclerView.setAdapter(mQuickAdapter);
//        mQuickAdapter.setOnLoadMoreListener(this);
//        mQuickAdapter.openLoadMore(StaticConfig.PAGE_SIZE);
//        mQuickAdapter.setAutoLoadMoreSize(StaticConfig.AUTO_SIZE);
//        // 一行代码搞定（默认为渐显效果）
//        mQuickAdapter.openLoadAnimation();
//        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
//        // mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        mRecyclerView.post(this::QueryItem);
//    }
//
//    @Override
//    public void onRefresh() {
//        // 刷新查询
//        RefreshCreditsHistory();
//        QueryUserCredit();
//    }
//
//    /**
//     * 查询登陸用户积分
//     */
//    private void QueryUserCredit() {
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//                        .getUser(userId, "_User[credit]").map(user -> user.getCreditByUserId().toString().trim()),
//                credit ->
//                        tv_credits.setText(credit),
//                throwable -> {
//                    tv_credits.setText("获取异常，请重新尝试");
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//    /**
//     * 查询登陸用户积分记录
//     */
//    private void RefreshCreditsHistory() {
//        Map<String, Object> queryMap = new HashMap<>();
//        toSubscribe(RxFactory.getCreditsDetailsServiceInstance(null)
//                        .queryCreditsDetails(queryMap)
//                        .map(CreditsHistory::getElements),
//                () -> {
//                    //隐藏无网络和无数据界面
//                    rl_no_network.setVisibility(View.GONE);
//                    rl_no_data.setVisibility(View.GONE);
//                    curPage = 1;//重置页码
//                    queryMap.put("page", curPage);
//                    queryMap.put("where", "[{\"key\":\"userId\",\"value\":\"" + userId + "\",\"operation\":\"=\",\"relation\":\"\"}," +
//                            "{\"key\":\"change\",\"value\":\"0\",\"operation\":\"!=\",\"relation\":\"and\"}]");
//                },
//                creditsHistories -> {
//                    if (creditsHistories.isEmpty()) {
//                        rl_no_data.setVisibility(View.VISIBLE);
//                        rl_no_data.setOnClickListener(v -> RefreshCreditsHistory());
//                    } else {
//                        curPage++;
//                    }
//                    mQuickAdapter.setNewData(creditsHistories);
//                    mCurrentCounter = mQuickAdapter.getData().size();
//                    mQuickAdapter.removeAllFooterView();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                },
//                throwable -> {
//                    rl_no_network.setVisibility(View.VISIBLE);
//                    rl_no_network.setOnClickListener(v -> RefreshCreditsHistory());
//                    mCurrentCounter = 0;
//                    mQuickAdapter.getData().clear();
//                    mQuickAdapter.removeAllFooterView();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//
//    /**
//     * 查询积分记录总数量
//     */
//    private void QueryItem() {
//        Map<String, Object> queryMap = new HashMap<>();
//        toSubscribe(RxFactory.getCreditsDetailsServiceInstance(null)
//                        .queryCreditsDetails(queryMap),
//                () -> {
//                    queryMap.put("page", curPage);
//                    queryMap.put("count", "1");
//                    queryMap.put("where", "[{\"key\":\"userId\",\"value\":\"" + userId + "\",\"operation\":\"=\",\"relation\":\"\"}," +
//                            "{\"key\":\"change\",\"value\":\"0\",\"operation\":\"!=\",\"relation\":\"and\"}]");
//                },
//                creditsHistory -> {
//                    // 一定要在mRecyclerView.post里面更新数据。
//                    mRecyclerView.post(() -> {
//                        // 如果有下一页则调用addData，不需要把下一页数据add到list里面，直接新的数据给adapter即可。
//                        mQuickAdapter.addData(creditsHistory.getElements());
//                        mCurrentCounter = mQuickAdapter.getData().size();
//                        if (mCurrentCounter >= Integer.parseInt(creditsHistory.getCount())) {
//                            // 数据全部加载完毕就调用 loadComplete
//                            mQuickAdapter.loadComplete();
//                            if (notLoadingView == null) {
//                                notLoadingView = getLayoutInflater().inflate(R.layout.include_index_not_loading,
//                                        (ViewGroup) mRecyclerView.getParent(), false);
//                                ((TextView) notLoadingView.findViewById(R.id.tv_not_loading)).setText("暂无更多积分纪录");
//                            }
//                            mQuickAdapter.addFooterView(notLoadingView);
//                        } else {
//                            curPage++;
//                        }
//                    });
//                },
//                throwable -> {
//                    mQuickAdapter.showLoadMoreFailedView();
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//    @Override
//    protected void onDestroy() {
//        AlibcTradeSDK.destory();
//        super.onDestroy();
//    }
//}
