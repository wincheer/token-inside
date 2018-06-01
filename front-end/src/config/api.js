import wepy from 'wepy';

// 服务器地址
const HOST = 'http://localhost:8080';
// Restful 接口
export const LOGIN = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/login`, data: params }).then(res => res.data); };

export const SELECT_PROJECT_LIST = (params) => { return wepy.request({ method: 'POST', url: `${HOST}/selectProjectList`, data: params }).then(res => res.data); };