const webpack = require("webpack");

module.exports = {
    devServer: {
        port: 4000,
        proxy: {
            '/pc/shortUrl': {
                target: 'http://suo.im/api.htm',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/mine/pc/shortUrl': '',//重写,
                }
            },
            '/pc/QRcode': {
                target: 'http://apis.juhe.cn/qrcode/api',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/mine/pc/QRcode': '',//重写,
                }
            },
        }
    },
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "window.jQuery": "jquery",
                Popper: ["popper.js", "default"]
            })
        ]
    },

};