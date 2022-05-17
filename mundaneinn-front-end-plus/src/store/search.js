import city from "@/api/search";
/**
 * 搜索框的全局数据
 */
const search = {
    state: {
        /*所有城市*/
        allCity:[],
        /*字母排序的所有城市*/
        orderAllCity:[],
        /*城市*/
        cityName:'成都市',
        /*城市编码*/
        cityCode:'5101',
        /*具体位置*/
        region:'',
        /*具体经纬度*/
        point:{"lng":"","lat":""},
        /*时间范围*/
        dateRange:'',
    },

    mutations: {
        SET_ALLCITY: (state, allCity) => {
            state.allCity = allCity
        },
        SET_ORDERALLCITY: (state, orderAllCity) => {
            state.orderAllCity = orderAllCity
        },
        SET_CITYNAME: (state, cityName) => {
            state.cityName = cityName
        },
        SET_CITYCODE: (state, cityCode) => {
            state.cityCode = cityCode
        },
        SET_REGION: (state, region) => {
            state.region = region
        },
        SET_POINT: (state,point) => {
            state.point = point
        },
        SET_DATERANGE: (state, dateRange) => {
            state.dateRange = dateRange
        }
    },

    actions: {
        getAllCity({commit}){
            return new Promise((resolve, reject) => {
                city.getAllCity("0").then((res)=>{
                    commit('SET_ALLCITY',res.data)
                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        getOrderAllCity({commit}){
            return new Promise((resolve, reject) => {
                city.getAllCity("1").then((res)=>{
                    commit('SET_ORDERALLCITY',res.data)
                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        setSearchParam({commit},cityName,cityCode,region,point,dateRange){
            commit('SET_CITYNAME', cityName)
            commit('SET_CITYCODE', cityCode)
            commit('SET_REGION', region)
            commit('SET_POINT', point)
            commit('SET_DATERANGE', dateRange)
        }

    }
  }

  export default search
