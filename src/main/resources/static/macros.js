// 学历
export const DEGREE_TYPE = [
  { id: 5, name: "专科" },
  { id: 4, name: "本科" },
  { id: 3, name: "硕士" },
  { id: 2, name: "博士" },
  { id: 1, name: "其他" }
];

// 学院
export const COLLEGE_TYPE = [
  { id: 0, name: "其他" },
  { id: 1, name: "建筑学院" },
  { id: 2, name: "机械工程学院" },
  { id: 3, name: "能源与环境学院" },
  { id: 4, name: "信息科学与工程学院" },
  { id: 5, name: "土木工程学院" },
  { id: 6, name: "电子科学与工程学院" },
  { id: 7, name: "数学学院" },
  { id: 9, name: "计算机科学与工程学院" },
  { id: 8, name: "自动化学院" },
  { id: 10, name: "物理学院" },
  { id: 11, name: "生物科学与医学工程学院" },
  { id: 12, name: "材料科学与工程学院" },
  { id: 13, name: "人文学院" },
  { id: 14, name: "经济管理学院" },
  { id: 15, name: "电气工程学院" },
  { id: 16, name: "外国语学院" },
  { id: 17, name: "体育系" },
  { id: 18, name: "化学化工学院" },
  { id: 19, name: "交通学院" },
  { id: 20, name: "仪器科学与工程学院" },
  { id: 21, name: "艺术学院" },
  { id: 22, name: "法学院" },
  { id: 23, name: "医学院" },
  { id: 24, name: "公共卫生学院" },
  { id: 25, name: "吴健雄学院" },
  { id: 26, name: "软件学院" },
  { id: 27, name: "海外教育学院" },
  { id: 28, name: "微电子学院" },
  { id: 29, name: "马克思主义学院" },
  { id: 30, name: "网络空间安全学院" },
  { id: 31, name: "人工智能学院" },
  { id: 32, name: "雷恩研究生学院" },
  { id: 33, name: "蒙纳士大学苏州联合研究生院" },
  { id: 34, name: "生命科学与技术学院" }
];

//资讯
//注意：每个params第一个对象必须表示搜索关键词
export const NEWS_TYPE = [
  {
    api:'Jobfairs_Search',
    name:'招聘会',
    params:[
      {zh:'关键词',en:'keyWord'},
      {zh:'范围',en:'range'},
      {zh:'招聘会类型',en:'type'},
      {zh:'举办城市',en:'city'},
      {zh:'举办时间',en:'time'}
    ],
    fields:[
      {zh:'招聘会名称',en:'title'},
      {zh:'举办城市',en:'city'},
      {zh:'举办地点',en:'location'},
      {zh:'招聘会类型',en:'type'},
      {zh:'举办时间',en:'time'}
    ]
  },
  {
    api:'Lectures_Search',
    name:'宣讲会',
    params:[
      {zh:'关键词',en:'keyWord'},
      {zh:'举办校区',en:'zone'},
      {zh:'举办时间',en:'time'}
    ],
    fields:[
      {zh:'宣讲会名称',en:'title'},
      {zh:'举办城市',en:'city'},
      {zh:'举办地点',en:'location'},
      {zh:'举办时间',en:'time'}
    ]
  },
  {
    api:'Posts_Search',
    name:'岗位',
    params:[
      {zh:'关键词',en:'keyWord'},
      {zh:'搜索字段',en:'title_type'},// 1为公司，2为职位
      {zh:'岗位类别',en:'postType'},// 0为全职，1为实习
      {zh:'行业类别',en:'d_industry'},
      {zh:'职能类别',en:'d_skill'},
      {zh:'地区',en:'city'},
      {zh:'薪资',en:'d_salary'},
      {zh:'学历要求',en:'d_education'},
      {zh:'发布时间',en:'time'},
      {zh:'单位性质',en:'nature'},
      {zh:'单位规模',en:'scale'},

    ],
    fields:[
      {zh:'职位名称',en:'postName'},
      {zh:'单位名称',en:'companyName'},
      {zh:'工作地点',en:'city'},
      {zh:'行业类别',en:'industry'},
      {zh:'发布日期',en:'time'}
    ]
  },
  {
    api:'Int_Organ_Search',
    name:'国际组织',
    params:[
      {zh:'标题关键词',en:'keyWord'}
    ],
    fields:[
      {zh:'标题',en:'title'},
      {zh:'发布时间',en:'time'}
    ]
  },
  {
    api:'Selected_Graduate_Search',
    name:'基层选调',

    params:[
      {zh:'标题关键词',en:'keyWord'}
    ],
    fields:[
      {zh:'标题',en:'title'},
      {zh:'发布时间',en:'time'}
    ]
  },
  {
    api:'Recru_Search',
    name:'招聘公告',
    params:[
      {zh:'关键词',en:'keyWord'},
      {zh:'范围',en:'range'},
      {zh:'工作城市',en:'city'},
      {zh:'发布时间',en:'time'},
    ],
    fields:[
      {zh:'公告标题',en:'title'},
      {zh:'工作城市',en:'city'},
      {zh:'发布时间',en:'time'}
    ]
  },
];

export const NEWS_PARAMS_TYPE = {
  time:{
    zh:'举办时间',
    values:[
      {zh:'全部',value:'1'},
      {zh:'近一周',value:'7'},
      {zh:'近一月',value:'30'},
      {zh:'近一年',value:'365'}
    ]
  },
  type:{
    zh:'招聘会类型',
    values:[
      {zh:'全部',value:'0'},
      {zh:'社会招聘会',value:'1'},
      {zh:'校园招聘会',value:'2'},
      {zh:'网络招聘会',value:'3'},
      {zh:'组团招聘会',value:'5'}
    ]
  },
  range:{
    zh:'范围',
    values:[
      {zh:'全部',value:'0'},
      {zh:'校内发布',value:'1'},
      {zh:'校外发布',value:'2'}
    ]
  },
  city:{
    zh:'举办省市',
    values:[
      {zh:'北京市',value:'110000'},
      {zh:'天津市',value:'120000'},
      {zh:'河北省',value:'130000'},
      {zh:'山西省',value:'140000'},
      {zh:'内蒙古自治区',value:'150000'},
      {zh:'辽宁省',value:'210000'},
      {zh:'吉林省',value:'220000'},
      {zh:'黑龙江省',value:'230000'},
      {zh:'上海市',value:'310000'},
      {zh:'江苏省',value:'320000'},
      {zh:'浙江省',value:'330000'},
      {zh:'安徽省',value:'340000'},
      {zh:'福建省',value:'350000'},
      {zh:'江西省',value:'360000'},
      {zh:'山东省',value:'370000'},
      {zh:'河南省',value:'410000'},
      {zh:'湖北省',value:'420000'},
      {zh:'湖南省',value:'430000'},
      {zh:'广东省',value:'440000'},
      {zh:'广西壮族自治区',value:'450000'},
      {zh:'海南省',value:'460000'},
      {zh:'重庆市',value:'500000'},
      {zh:'四川省',value:'510000'},
      {zh:'贵州省',value:'520000'},
      {zh:'云南省',value:'530000'},
      {zh:'西藏自治区',value:'540000'},
      {zh:'陕西省',value:'610000'},
      {zh:'甘肃省',value:'620000'},
      {zh:'青海省',value:'630000'},
      {zh:'宁夏回族自治区',value:'640000'},
      {zh:'新疆维吾尔自治区',value:'650000'},
      {zh:'台湾省',value:'710000'},
      {zh:'香港特别行政区',value:'810000'},
      {zh:'澳门特别行政区',value:'820000'},
      {zh:'海外',value:'900000'}
    ]
  },
  zone:{
    zh:'举办校区',
    values:[
      {zh:'全部',value:null},
      {zh:'四牌楼校区',value:'四牌楼校区'},
      {zh:'九龙湖校区',value:'九龙湖校区'}
    ]
  },
  title_type:{
    zh:'搜索字段',
    values:[
      {zh:'按公司名搜索',value:'1'},
      {zh:'按职位名搜索',value:'2'}
    ]
  },
  postType:{
    zh:'岗位类别',
    values:[
      {zh:'全职岗位',value:'0'},
      {zh:'实习岗位',value:'1'}
    ]
  },
  d_industry:{
    zh:'行业类别',
    values:[
      {zh:'农业',value:'A01'},
      {zh:'林业',value:'A02'},
      {zh:'畜牧业',value:'A03'},
      {zh:'渔业',value:'A04'},
      {zh:'农、林、牧、渔专业及辅助性活动',value:'A05'},
      {zh:'煤炭开采和洗选业',value:'B06'},
      {zh:'石油和天然气开采业',value:'B07'},
      {zh:'黑色金属矿采选业',value:'B08'},
      {zh:'有色金属矿采选业',value:'B09'},
      {zh:'非金属矿采选业',value:'B10'},
      {zh:'开采专业及辅助性活动',value:'B11'},
      {zh:'其他采矿业',value:'B12'},
      {zh:'农副食品加工业',value:'C13'},
      {zh:'食品制造业',value:'C14'},
      {zh:'酒、饮料和精制茶制造业',value:'C15'},
      {zh:'烟草制品业',value:'C16'},
      {zh:'纺织业',value:'C17'},
      {zh:'纺织服装、服饰业',value:'C18'},
      {zh:'皮革、毛皮、羽毛及其制品和制鞋业',value:'C19'},
      {zh:'木材加工和木、竹、藤、棕、草制品业',value:'C20'},
      {zh:'家具制造业',value:'C21'},
      {zh:'造纸和纸制品业',value:'C22'},
      {zh:'印刷和记录媒介复制业',value:'C23'},
      {zh:'文教、工美、体育和娱乐用品制造业',value:'C24'},
      {zh:'石油、煤炭及其他燃料加工业',value:'C25'},
      {zh:'化学原料和化学制品制造业',value:'C26'},
      {zh:'医药制造业',value:'C27'},
      {zh:'化学纤维制造业',value:'C28'},
      {zh:'橡胶和塑料制品业',value:'C29'},
      {zh:'非金属矿物制品业',value:'C30'},
      {zh:'黑色金属冶炼和压延加工业',value:'C31'},
      {zh:'有色金属冶炼和压延加工业',value:'C32'},
      {zh:'金属制品业',value:'C33'},
      {zh:'通用设备制造业',value:'C34'},
      {zh:'专用设备制造业',value:'C35'},
      {zh:'汽车制造业',value:'C36'},
      {zh:'铁路、船舶、航空航天和其他运输设备制造业',value:'C37'},
      {zh:'电气机械和器材制造业',value:'C38'},
      {zh:'计算机、通信和其他电子设备制造业',value:'C39'},
      {zh:'仪器仪表制造业',value:'C40'},
      {zh:'其他制造业',value:'C41'},
      {zh:'废弃资源综合利用业',value:'C42'},
      {zh:'金属制品、机械和设备修理业',value:'C43'},
      {zh:'电力、热力生产和供应业',value:'D44'},
      {zh:'燃气生产和供应业',value:'D45'},
      {zh:'水的生产和供应业',value:'D46'},
      {zh:'房屋建筑业',value:'E47'},
      {zh:'土木工程建筑业',value:'E48'},
      {zh:'建筑安装业',value:'E49'},
      {zh:'建筑装饰、装修和其他建筑业',value:'E50'},
      {zh:'批发业',value:'F51'},
      {zh:'零售业',value:'F52'},
      {zh:'铁路运输业',value:'G53'},
      {zh:'道路运输业',value:'G54'},
      {zh:'水上运输业',value:'G55'},
      {zh:'航空运输业',value:'G56'},
      {zh:'管道运输业',value:'G57'},
      {zh:'多式联运和运输代理业',value:'G58'},
      {zh:'装卸搬运和仓储业',value:'G59'},
      {zh:'邮政业',value:'G60'},
      {zh:'住宿业',value:'H61'},
      {zh:'餐饮业',value:'H62'},
      {zh:'电信、广播电视和卫星传输服务',value:'I63'},
      {zh:'互联网和相关服务',value:'I64'},
      {zh:'软件和信息技术服务业',value:'I65'},
      {zh:'货币金融服务',value:'J66'},
      {zh:'资本市场服务',value:'J67'},
      {zh:'保险业',value:'J68'},
      {zh:'其他金融业',value:'J69'},
      {zh:'房地产业',value:'K70'},
      {zh:'租赁业',value:'L71'},
      {zh:'商务服务业',value:'L72'},
      {zh:'研究和试验发展',value:'M73'},
      {zh:'专业技术服务业',value:'M74'},
      {zh:'科技推广和应用服务业',value:'M75'},
      {zh:'水利管理业',value:'N76'},
      {zh:'生态保护和环境治理业',value:'N77'},
      {zh:'公共设施管理业',value:'N78'},
      {zh:'土地管理业',value:'N79'},
      {zh:'居民服务业',value:'O80'},
      {zh:'机动车、电子产品和日用产品修理业',value:'O81'},
      {zh:'其他服务业',value:'O82'},
      {zh:'教育',value:'P83'},
      {zh:'卫生',value:'Q84'},
      {zh:'社会工作',value:'Q85'},
      {zh:'新闻和出版业',value:'R86'},
      {zh:'广播、电视、电影和录音制作业',value:'R87'},
      {zh:'文化艺术业',value:'R88'},
      {zh:'体育',value:'R89'},
      {zh:'娱乐业',value:'R90'},
      {zh:'中国共产党机关',value:'S91'},
      {zh:'国家机构',value:'S92'},
      {zh:'人民政协、民主党派',value:'S93'},
      {zh:'社会保障',value:'S94'},
      {zh:'群众团体、社会团体和其他成员组织',value:'S95'},
      {zh:'基层群众自治组织及其他组织',value:'S96'},
      {zh:'国际组织',value:'T97'}
      
    ]
  },
  d_skill:{
    zh:'职能类别',
    values:[
      {zh:'公务员',value:'10'},
      {zh:'科学研究人员',value:'11'},
      {zh:'工程技术人员',value:'13'},
      {zh:'农林牧渔业技术人员',value:'17'},
      {zh:'卫生专业技术人员',value:'19'},
      {zh:'经济业务人员',value:'21'},
      {zh:'金融业务人员',value:'22'},
      {zh:'法律专业人员',value:'23'},
      {zh:'教学人员',value:'24'},
      {zh:'文学艺术工作人员',value:'25'},
      {zh:'体育工作人员',value:'26'},
      {zh:'新闻出版和文化工作人员',value:'27'},
      {zh:'其他专业技术人员',value:'29'},
      {zh:'办事人员和有关人员',value:'30'},
      {zh:'商业和服务业人员',value:'40'},
      {zh:'生产和运输设备操作人',value:'60'},
      {zh:'军人',value:'80'},
      {zh:'其他人员',value:'90'}
    ]
  },
  d_salary:{
    zh:'薪资',
    values:[
      {zh:'1500以下',value:'100'},
      {zh:'1500~1999',value:'101'},
      {zh:'2000~2999',value:'102'},
      {zh:'3000~4499',value:'103'},
      {zh:'4500~5999',value:'104'},
      {zh:'6000~7999',value:'105'},
      {zh:'8000~9999',value:'106'},
      {zh:'10000~14999',value:'107'},
      {zh:'15000~19999',value:'108'},
      {zh:'20000~29999',value:'109'},
      {zh:'30000~49999',value:'110'},
      {zh:'50000及以上',value:'111'},
    ]
  },
  d_education:{
    zh:'学历要求',
    values:[
      {zh:'大专',value:'100'},
      {zh:'本科',value:'101'},
      {zh:'硕士',value:'102'},
      {zh:'博士',value:'103'}
    ]
  },
  nature:{
    zh:'单位性质',
    values:[
      {zh:'国有及集体企业',value:'150001'},
      {zh:'民营企业',value:'150002'},
      {zh:'港澳台及外资企业',value:'150003'},
      {zh:'事业单位',value:'150005'},
      {zh:'社会团体',value:'150006'},
      {zh:'国家机关',value:'150007'},
      {zh:'部队',value:'150013'},
      {zh:'个体工商户',value:'150018'},
    ]
  },
  scale:{
    zh:'单位规模',
    values:[
      {zh:'少于50人',value:'100'},
      {zh:'50~300人',value:'101'},
      {zh:'301~1000人',value:'103'},
      {zh:'1001人以上',value:'104'}
    ]
  },
};

