const webpack = require("webpack");

module.exports = {
    devServer: {
        port: 80,
        proxy: {
            '/api/getShortUrl': {
                target: 'http://suo.im/api.htm',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/api/getShortUrl': '',//重写,
                }
            },
            '/api/getQRcode': {
                target: 'http://apis.juhe.cn/qrcode/api',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/api/getQRcode': '',//重写,
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
    }
};