import wepy from 'wepy';

// 服务器地址
const HOST = 'https://ti.commercelabcn.com';
// const HOST = 'http://139.159.238.218:80';
// Restful 接口
export const SELECT_APP_USER = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/selectAppUser`, data: params }).then(res => res.data); };

export const SELECT_TOKEN_INFO = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/selectTokenInfo`, data: params }).then(res => res.data); };
export const SELECT_WALLET_INFO = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/selectWalletInfo`, data: params }).then(res => res.data); };
export const QUERY_BALANCE_OF = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/queryBalanceOf`, data: params }).then(res => res.data); };
export const SELECT_GRAPH_INFO = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/selectGraphInfo`, data: params }).then(res => res.data); };