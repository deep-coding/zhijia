!function() {
        var win = window;
        var doc = win.document;
        var docEl = doc.documentElement;
        var metaEl = doc.querySelector('meta[name="viewport"]');
        var flexibleEl = doc.querySelector('meta[name="flexible"]');
        var dpr;
        var scale;
        var tid;
        
        if (metaEl) {
            console.warn('set scale by meta');
            var match = metaEl.getAttribute('content').match(/initial\-scale=(["']?)([\d\.]+)\1?/);
            if (match) {
                scale = parseFloat(match[2]);
                dpr = parseInt(1 / scale);
            }
        } else if (flexibleEl) {
            dpr = parseInt(flexibleEl.getAttribute('data-dpr'));
            scale = parseFloat((1 / dpr).toFixed(2));
        }
        
        if (!dpr && !scale) {
            var isAndroid = win.navigator.appVersion.match(/android/gi);
            var isIPhone = win.navigator.appVersion.match(/iphone/gi);
            var dpr = win.devicePixelRatio;
            if (isAndroid) {
                // ��׿�£��Ծ�ʹ��1���ķ���
                dpr = 1;
            } else if (isIPhone) {
                // iOS�£�����2��3��������2���ķ������������1������
                if (dpr >= 2) {
                    dpr = 2;
                } else {
                    dpr = 1;
                }
            }
            scale = 1 / dpr;
        }
        
        docEl.setAttribute('data-dpr', dpr);
        if (!metaEl) {
            metaEl = doc.createElement('meta');
            metaEl.setAttribute('name', 'viewport');
            metaEl.setAttribute('content', 'initial-scale=' + scale + ', maximum-scale=' + scale + ', minimum-scale=' + scale + ', user-scalable=no');
            if (docEl.firstElementChild) {
                docEl.firstElementChild.appendChild(metaEl);
            } else {
                var wrap = doc.createElement('div');
                wrap.appendChild(metaEl);
                doc.write(wrap.innerHTML);
            }
        }
        
        function setUnitA() {
            var width = docEl.getBoundingClientRect().width;
            if (width / dpr > 540) {
                width = 540 * dpr;
            }
            win.rem = Math.floor(width / 16);
            docEl.style.fontSize = win.rem + 'px';
        }
        
        win.dpr = dpr;
        win.addEventListener('resize', function () {
            clearTimeout(tid);
            tid = setTimeout(setUnitA, 300);
        }, false);
        win.addEventListener('pageshow', function (e) {
            if (e.persisted) {
                clearTimeout(tid);
                tid = setTimeout(setUnitA, 300);
            }
        }, false);
        
        if (doc.readyState === 'complete') {
            doc.body.style.fontSize = 12 * dpr + 'px';
        } else {
            doc.addEventListener('DOMContentLoaded', function (e) {
                doc.body.style.fontSize = 12 * dpr + 'px';
            }, false);
        }
        
        setUnitA();
    }();