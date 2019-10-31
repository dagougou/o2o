<html>
<link rel="stylesheet" href="resources/dialog/css/dialog.css" />
<body>
<h2>Hello World!</h2>
<button id="btn">button</button>
<p>nihao</p>
</body>
<script type="text/javascript" src="resources/js/base/jquery-3.3.1.min.js"></script>
<script src="resources/dialog/lib/zepto.min.js"></script>
<script src="resources/dialog/js/dialog.js"></script>

<script>
    $(document).on('click', '#btn', function() {
       var n = $(document).dialog({
            type : 'toast',
            infoIcon: 'resources/dialog/images/icon/loading.gif',
            infoText: 'locding',
       });

       for(var i =0 ; i<500 ;i++){
           if(i==100){
               n.close();
           }
       }
    });
</script>
</html>
