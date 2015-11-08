<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path=request.getContextPath();
	request.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link href="${path }/liger/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
 <style type="text/css"> 
   body{ padding:0 4px 0 4px; margin:0; background:#EAEEF5; overflow:hidden;}
   #layout1{  width:100%;margin:0; padding:0;  }  
   
    /* 顶部 */ 
   .l-page-top{ height:31px; margin-bottom:3px; background:url('liger/images/top.jpg') repeat-x bottom;}
   .l-topmenu-logo{ color:#E7E7E7; padding-left:35px; line-height:26px;background:url('liger/images/topicon.gif') no-repeat 10px 5px; font-size: 20px; font-weight: bold;}
   .l-topmenu-welcome{  position:absolute; height:24px; line-height:24px;  right:30px; top:2px;color:#070A0C;}
   .l-topmenu-welcome a{ color:#E7E7E7; text-decoration:underline}
   .space{ color:#E7E7E7;}
   .l-topmenu-welcome label{color:white;}
   .l-topmenu-welcome span {color:white; margin-right: 30px;}
   
</style>
</head>
<body>  
	<div class="l-page-top">
	    <div class="l-topmenu-logo">CRM管理系统</div>
	    <div class="l-topmenu-welcome">
	    	<span>张三，欢迎登录！</span>
	        <label> 皮肤切换：</label>
	        <select id="skinSelect">
	            <option value="aqua">默认</option> 
	            <option value="silvery">Silvery</option>
	            <option value="gray">Gray</option>
	            <option value="gray2014">Gray2014</option>
	        </select>
	        <a href="http://www.ligerui.com/pay.html" class="l-link2" target="_blank">登录</a> 
	        <span class="space">|</span>
	        <a href="http://www.ligerui.com/server.html" class="l-link2" target="_blank">退出</a> 
	    </div> 
	</div>
	<div id="layout1">
	    <div position="left">
	    	<%-- <iframe frameborder="0" name="home" id="home" src="${path }/pageController/page?pageName=left_manage"></iframe> --%>
	    	<a target="home" href="${path }/user/userView?uuid=ebce196a6f404aa2b59e78c059fabed1">查看用户信息</a>
	    	<a class="l-link" target="home" href="javascript:f_addTab('home','工作日志','${path }/user/userView?uuid=ebce196a6f404aa2b59e78c059fabed1')">工作日志</a>  
	    </div>
	    <div position="center" id="framecenter"> 
            <div tabid="home" title="我的主页" style="height:500px" >
                <iframe frameborder="0" name="home" id="home" src="http://localhost:8083/task-web/index.jsp"></iframe>
            </div> 
        </div> 
	      
	 </div>
  
    <div >
        <div style="height:32px; line-height:32px; text-align:center;">Copyright © 2011-2014 www.wsy.com</div>
    </div>
    <div style="display:none"></div>
</body>

<script src="${path }/liger/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>   
<script src="${path }/liger/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${path }/liger/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script src="${path }/liger/ligerUI/js/plugins/ligerTab.js"></script>
<script type="text/javascript">
var tab = null;
   $(function (){
	   $("#layout1").ligerLayout({ leftWidth: 190, height: '94%'});
	   var height = $(".l-layout-center").height();
	 	//Tab
       $("#framecenter").ligerTab({
           height: height,
           showSwitchInTab : true,
           showSwitch: true,
           onAfterAddTabItem: function (tabdata)
           {
               tabItems.push(tabdata);
               saveTabStatus();
           },
           onAfterRemoveTabItem: function (tabid)
           { 
               for (var i = 0; i < tabItems.length; i++)
               {
                   var o = tabItems[i];
                   if (o.tabid == tabid)
                   {
                       tabItems.splice(i, 1);
                       saveTabStatus();
                       break;
                   }
               }
           },
           onReload: function (tabdata)
           {
               var tabid = tabdata.tabid;
               addFrameSkinLink(tabid);
           }
       });
	 	
       tab = $("#framecenter").ligerGetTabManager();

   });
   
   function f_addTab(tabid,text, url)
   {
	   
	   	//alert(tab);
		//tab.removeAll ();//单任务；
		tab.addTabItem({
		tabid : tabid,
		text: text,
		url: url
		});
		return true;
   }
</script> 
</html>

