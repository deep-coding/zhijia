
var Dialogs = function (){
    var win = window,
        doc = win.document,
        body = doc.body,
        DialogNode,        
        initDom = false;

    DialogNode = doc.createElement('div');
    ShadeNode = doc.createElement('div');
    DialogNode.className = 'dialog-wrapper alertMode hide';
    DialogNode.innerHTML = ['<div class="dialog-container">',
                                '<div class="dialog-title"></div>',
                                '<div class="dialog-body"></div>',
                                '<div class="dialog-footer">',
                                    '<button type="button" class="cancel">取消</button>',
                                    '<button type="button" class="confirm">确定</button>',
                                '</div>',
                            '</div>'].join('');
    ShadeNode.className = 'shade-wrapper';
    ShadeNode.innerHTML = '<div class="shade-wrapper"></div>';

    Dialog_container = DialogNode.querySelector('.dialog-container');
    Dialog_title = DialogNode.querySelector(".dialog-title");
    Dialog_body = DialogNode.querySelector(".dialog-body");
    Dialog_footer = DialogNode.querySelector(".dialog-footer");
    Dialog_cancelBtn = DialogNode.querySelector(".cancel");
    DialogNode_confirmBtn = DialogNode.querySelector(".confirm");

    function Dialog(options) {
        //默认配置
        this._options = {
        }

        this._options = _extend(this._options, options || {});
        this._init();
    }

    _extend(Dialog.prototype, {

        _init : function() {
            var self = this,
                opt  =  this._options,
                mode = opt.mode,
                title = opt.title,
                content = opt.content,
                footer = opt.footer,
                callback = opt.callback || function(){},
                _dialogClass = DialogNode.className;


            //设置className
            _dialogClass = _dialogClass.replace(/(alert|confirm|custom)Mode/i, mode + 'Mode');
            DialogNode.className = _dialogClass;

            //设置标题和内容
            title ? self.setTitle(title) : Dialog_title.style.display = "none";
            content && self.setBodyContent(content);
            footer && self.setFooter(footer);


            //重新赋值元素
            Dialog_container = DialogNode.querySelector('.dialog-container');
            Dialog_title = DialogNode.querySelector(".dialog-title");
            Dialog_body = DialogNode.querySelector(".dialog-body");
            Dialog_footer = DialogNode.querySelector(".dialog-footer");
            Dialog_cancelBtn = DialogNode.querySelector(".cancel");
            DialogNode_confirmBtn = DialogNode.querySelector(".confirm");


            //移除事件
            Dialog_cancelBtn.removeEventListener('click',_failureHandler,false);
            DialogNode_confirmBtn.removeEventListener('click',_successHandler,false);

            //绑定事件
            DialogNode_confirmBtn.addEventListener('click',_successHandler,false);
            Dialog_cancelBtn.addEventListener('click',_failureHandler,false);

            //设置callback
            DialogNode_confirmBtn.callback = Dialog_cancelBtn.callback = function(){
                callback.apply(self,arguments);
            }

            if (!initDom) {
                initDom = true;
                doc.body.appendChild(ShadeNode);
                doc.body.appendChild(DialogNode);
                win.addEventListener('resize', function() {
                    setTimeout(function() {
                        self._pos();
                    }, 500);
                }, false);
                setTimeout(function(){
                    self.show();
                },0)
            }
            else{
                self.show();
            }
        },

        _pos : function() {
            //居中显示
            var self = this,
                bodyRect = doc.body.getBoundingClientRect(),
                top = -bodyRect.top,
                left = -bodyRect.left,
                iW = win.innerWidth,
                iH = win.innerHeight,
                floatRect = DialogNode.getBoundingClientRect(),
                eW = floatRect.width,
                eH = floatRect.height;


            if (!self.isHide()) {
                DialogNode.style.left = (left + (iW - eW) / 2) + 'px';
                DialogNode.style.top = (top + (iH - eH) / 2) + 'px';
            }
        },

        isShow : function() {
            return DialogNode.className.indexOf('show') > -1;
        },

        isHide : function() {
            return DialogNode.className.indexOf('hide') > -1;
        },

        show : function() {
            var self = this,
                opt  = self._options;
            if (!self.isShow()) {
                DialogNode.style.display = "block";
                ShadeNode.style.display = "block";
                DialogNode.className = DialogNode.className.replace(/\s*show|hide/, '') + ' show';
                self._pos();
            }
        },

        hide : function() {
            var self = this;
            if (!self.isHide()) {
                DialogNode.style.display = "none";
                ShadeNode.style.display = "none";
                DialogNode.className = DialogNode.className.replace(/\s*show|hide/, '') + ' hide';
            }
        },

        setBodyContent: function(_html){
            Dialog_body.innerHTML = _html;
        },

        setTitle: function(_title){
            Dialog_title.innerHTML = _title 
        },

        setFooter: function(_footer){
            Dialog_footer.innerHTML = _footer;
        }


    });


    function _extend(a, b) {
        for (var k in b) {
            a[k] = b[k];
        }
        return a;
    }

    function _successHandler(){
        //隐藏Dialog
        _hideDialog();
        //回调函数 
        this.callback && this.callback({result: true})
    }

    function _failureHandler(){
        //隐藏Dialog
        _hideDialog();
        //回调函数
        this.callback && this.callback({result: false})
    }

    function _hideDialog(){
        DialogNode.style.display = "none";
        ShadeNode.style.display = "none";
        DialogNode.className = DialogNode.className.replace(/\s*show|hide/, '') + ' hide';
    }

    function _paseData(_config,_callback){
        var reConfig = {}, reCallback;
        if ($.isPlainObject(_config)){
            reConfig = _config;
        }
        else if ($.isFunction(_config)){
            _callback = _config;
        }

        if ($.isFunction(_callback)){
            reCallback = _callback;
        }

        return {
            config: reConfig,
            callback: reCallback
        }
    }

    return new function(){
        this.alert = function(msg,config,callback){
            var _data = _paseData(config,callback);
            _data.config = _extend(_data.config,{
                callback: _data.callback,
                content: msg,
                mode: "alert"
            })
            new Dialog(_data.config);
        }

        this.confirm = function(msg,config,callback){
            var _data = _paseData(config,callback);
            _data.config = _extend(_data.config,{
                callback: _data.callback,
                content: msg,
                mode: "confirm"
            })
            new Dialog(_data.config);
        }

        this.custom = function(config,callback){
            var _data = _paseData(config,callback);
            _data.config = _extend(_data.config,{
                callback: _data.callback,
                title: config.title,
                content: config.content,
                footer: config.footer,
                mode: "custom"
            })
            new Dialog(_data.config);
        }
    }
};