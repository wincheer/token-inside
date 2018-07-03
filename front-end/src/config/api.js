import wepy from 'wepy';

// 服务器地址
const HOST = 'http://localhost:8080';
// Restful 接口
export const LOGIN = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/login`, data: params }).then(res => res.data); };
export const SELECT_APP_USER = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/selectAppUser`, data: params }).then(res => res.data); };
export const BC_OVERVIEW = () => { return wepy.request({ method: 'POST', url: `${HOST}/bcOverview`}).then(res => res.data); };
export const SELECT_TOKEN_LIST_PAGE = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/selectTokenListPage`, data: params }).then(res => res.data); };