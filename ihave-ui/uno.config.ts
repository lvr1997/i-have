import {
    defineConfig,
    presetAttributify,
    presetIcons,
    presetUno,
    presetTypography,
    presetWebFonts,
    transformerDirectives,
    transformerVariantGroup,
} from 'unocss'

export default defineConfig({
    shortcuts: [
        ['btn', 'px-4 py-1 rounded inline-block bg-teal-700 text-white cursor-pointer !outline-none hover:bg-teal-800 disabled:cursor-default disabled:bg-gray-600 disabled:opacity-50'],
        ['icon-btn', 'inline-block cursor-pointer select-none opacity-75 transition duration-200 ease-in-out hover:opacity-100 hover:text-teal-600'],
    ],
    // 预设 Presets
    presets: [
        presetUno(),            // 默认原子化 CSS 规则
        presetAttributify(),    // 支持属性化写法，例如 <div text="red-500"></div>
        presetIcons({           // 图标支持
            scale: 1.2,         // 图标缩放比例
            warn: true,         // 控制台警告缺失图标
        }),
        presetTypography(),     // 排版支持
        presetWebFonts({
            fonts: {
                sans: 'DM Sans',
                serif: 'DM Serif Display',
                mono: 'DM Mono',
            },
        }),
    ],

    // 转换器 Transformers
    transformers: [
        transformerDirectives(),    // 支持 @apply、@screen 等指令
        transformerVariantGroup(),  // 支持变体分组，如 hover:(bg-red-500 text-white)
    ],

    safelist: 'prose prose-sm m-auto text-left'.split(' '), // 允许使用的类名

    // 可选：自定义规则
    rules: [
        ['m-auto', { margin: 'auto' }],
    ],
})