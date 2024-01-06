package com.blogspot.kotlinstudy.lognote

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ConfigManager private constructor() {
    companion object {
        private const val CONFIG_FILE = "lognote.xml"
        val LOGNOTE_HOME: String = System.getenv("LOGNOTE_HOME") ?: ""
        const val ITEM_CONFIG_VERSION = "CONFIG_VERSION"
        const val ITEM_FRAME_X = "FRAME_X"
        const val ITEM_FRAME_Y = "FRAME_Y"
        const val ITEM_FRAME_WIDTH = "FRAME_WIDTH"
        const val ITEM_FRAME_HEIGHT = "FRAME_HEIGHT"
        const val ITEM_FRAME_EXTENDED_STATE = "FRAME_EXTENDED_STATE"
        const val ITEM_ROTATION = "ROTATION"
        const val ITEM_DIVIDER_LOCATION = "DIVIDER_LOCATION"
        const val ITEM_LAST_DIVIDER_LOCATION = "LAST_DIVIDER_LOCATION"

        const val ITEM_LANG = "LANG"

        const val ITEM_SHOW_LOG = "SHOW_LOG_"
        const val COUNT_SHOW_LOG = 20
        const val ITEM_TOKEN_FILTER = "TOKEN_FILTER_"
        const val COUNT_TOKEN_FILTER = 10

        const val ITEM_HIGHLIGHT_LOG = "HIGHLIGHT_LOG_"
        const val COUNT_HIGHLIGHT_LOG = 10

        const val ITEM_SEARCH_LOG = "SEARCH_LOG_"
        const val COUNT_SEARCH_LOG = 10

        const val ITEM_SEARCH_MATCH_CASE = "SEARCH_MATCH_CASE"

        const val ITEM_SHOW_LOG_CHECK = "SHOW_LOG_CHECK"
        const val ITEM_TOKEN_CHECK = "TOKEN_CHECK_"

        const val ITEM_HIGHLIGHT_LOG_CHECK = "HIGHLIGHT_LOG_CHECK"

        const val ITEM_LOG_LEVEL = "LOG_LEVEL"

        const val ITEM_LOOK_AND_FEEL = "LOOK_AND_FEEL"
        const val ITEM_UI_FONT_SIZE = "UI_FONT_SIZE"
        const val ITEM_APPEARANCE_DIVIDER_SIZE = "APPEARANCE_DIVIDER_SIZE"

        const val ITEM_ADB_DEVICE = "ADB_DEVICE"
        const val ITEM_ADB_CMD = "ADB_CMD"
        const val ITEM_ADB_LOG_CMD = "ADB_LOG_CMD"
        const val ITEM_ADB_LOG_SAVE_PATH = "ADB_LOG_SAVE_PATH"
        const val ITEM_ADB_PREFIX = "ADB_PREFIX"
        const val ITEM_ADB_OPTION_1 = "ADB_OPTION_1"

        const val ITEM_FONT_NAME = "FONT_NAME"
        const val ITEM_FONT_SIZE = "FONT_SIZE"
        const val ITEM_VIEW_FULL = "VIEW_FULL"
        const val ITEM_FILTER_INCREMENTAL = "FILTER_INCREMENTAL"
        const val ITEM_FILTER_BY_FILE = "FILTER_BY_FILE"
        const val ITEM_COLOR_TAG_REGEX = "COLOR_TAG_REGEX"

        const val ITEM_SCROLLBACK = "SCROLLBACK"
        const val ITEM_SCROLLBACK_SPLIT_FILE = "SCROLLBACK_SPLIT_FILE"
        const val ITEM_MATCH_CASE = "MATCH_CASE"

        const val ITEM_FILTERS_TITLE = "FILTERS_TITLE_"
        const val ITEM_FILTERS_FILTER = "FILTERS_FILTER_"
        const val ITEM_FILTERS_TABLEBAR = "FILTERS_TABLEBAR_"

        const val ITEM_CMDS_TITLE = "CMDS_TITLE_"
        const val ITEM_CMDS_CMD = "CMDS_CMD_"
        const val ITEM_CMDS_TABLEBAR = "CMDS_TABLEBAR_"

        const val ITEM_COLOR_MANAGER = "COLOR_MANAGER_"
        const val ITEM_COLOR_FILTER_STYLE = "COLOR_FILTER_STYLE_"

        const val ITEM_RETRY_ADB = "RETRY_ADB"

        const val ITEM_SHOW_LOG_STYLE = "SHOW_LOG_STYLE"
        const val ITEM_BOLD_LOG_STYLE = "BOLD_LOG_STYLE"
        const val ITEM_TOKEN_COMBO_STYLE = "TOKEN_COMBO_STYLE_"

        const val ITEM_ICON_TEXT = "ICON_TEXT"
        const val VALUE_ICON_TEXT_I_T = "IconText"
        const val VALUE_ICON_TEXT_I = "Icon"
        const val VALUE_ICON_TEXT_T = "Text"

        var LaF = ""

        private val mInstance: ConfigManager = ConfigManager()

        fun getInstance(): ConfigManager {
            return mInstance
        }
    }

    private val mProperties = Properties()
    private var mConfigPath = CONFIG_FILE

    init {
        if (LOGNOTE_HOME.isNotEmpty()) {
            mConfigPath = "$LOGNOTE_HOME${File.separator}$CONFIG_FILE"
        }
        println("Config Path : $mConfigPath")
        manageVersion()
    }

    private fun setDefaultConfig() {
        mProperties[ITEM_LOG_LEVEL] = FormatManager.LEVEL_VERBOSE.toString()
        mProperties[ITEM_SHOW_LOG_CHECK] = "true"
        for (idx in 0 until FormatManager.MAX_TOKEN_COUNT) {
            mProperties["$ITEM_TOKEN_CHECK$idx"] = "false"
        }
        mProperties[ITEM_HIGHLIGHT_LOG_CHECK] = "true"
    }

    fun loadConfig(): Boolean {
        var ret = true
        var fileInput: FileInputStream? = null

        try {
            fileInput = FileInputStream(mConfigPath)
            mProperties.loadFromXML(fileInput)
        } catch (ex: Exception) {
            ex.printStackTrace()
            setDefaultConfig()
            ret = false
        } finally {
            if (null != fileInput) {
                try {
                    fileInput.close()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
        }

        return ret
    }

    fun saveConfig(): Boolean {
        var ret = true
        var fileOutput: FileOutputStream? = null
        try {
            fileOutput = FileOutputStream(mConfigPath)
            mProperties.storeToXML(fileOutput, "")
        } catch (ex: Exception) {
            ex.printStackTrace()
            ret = false
        } finally {
            if (null != fileOutput) {
                try {
                    fileOutput.close()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
        }
        return ret
    }

    fun saveItem(key: String, value: String) {
        loadConfig()
        setItem(key, value)
        saveConfig()
    }

    fun saveItems(keys: Array<String>, values: Array<String>) {
        loadConfig()
        setItems(keys, values)
        saveConfig()
    }

    fun getItem(key: String): String? {
        return mProperties[key] as String?
    }

    fun setItem(key: String, value: String) {
        mProperties[key] = value
    }

    private fun setItems(keys: Array<String>, values: Array<String>) {
        if (keys.size != values.size) {
            println("saveItem : size not match ${keys.size}, ${values.size}")
            return
        }
        for (idx in keys.indices) {
            mProperties[keys[idx]] = values[idx]
        }
    }

    fun removeConfigItem(key: String) {
        mProperties.remove(key)
    }

    fun saveFontColors(family: String, size: Int) {
        loadConfig()

        mProperties[ITEM_FONT_NAME] = family
        mProperties[ITEM_FONT_SIZE] = size.toString()
        ColorManager.getInstance().mFullTableColor.putConfig()
        ColorManager.getInstance().mFilterTableColor.putConfig()

        saveConfig()
    }

    fun saveFilterStyle(
        keys: Array<String>,
        values: Array<String>,
        tokenKeys: Array<String>,
        tokenValues: Array<String>
    ) {
        loadConfig()
        setItems(keys, values)
        setItems(tokenKeys, tokenValues)
        ColorManager.getInstance().putConfigFilterStyle()
        saveConfig()
    }

    fun loadFilters() : ArrayList<CustomListManager.CustomElement> {
        val filters = ArrayList<CustomListManager.CustomElement>()

        var title: String
        var filter: String
        var check: String
        var tableBar: Boolean
        for (i in 0 until FiltersManager.MAX_FILTERS) {
            title = (mProperties[ITEM_FILTERS_TITLE + i] ?: "") as String
            if (title.isEmpty()) {
                break
            }
            filter = (mProperties[ITEM_FILTERS_FILTER + i] ?: "null") as String
            check = (mProperties[ITEM_FILTERS_TABLEBAR + i] ?: "false") as String
            tableBar = check.toBoolean()

            filters.add(CustomListManager.CustomElement(title, filter, tableBar))
        }

        return filters
    }

    fun saveFilters(filters : ArrayList<CustomListManager.CustomElement>) {
        loadConfig()

        var nCount = filters.size
        if (nCount > FiltersManager.MAX_FILTERS) {
            nCount = FiltersManager.MAX_FILTERS
        }

        for (i in 0 until FiltersManager.MAX_FILTERS) {
            val title: String = (mProperties[ITEM_FILTERS_TITLE + i] ?: "") as String
            if (title.isEmpty()) {
                break
            }
            mProperties.remove(ITEM_FILTERS_TITLE + i)
            mProperties.remove(ITEM_FILTERS_FILTER + i)
            mProperties.remove(ITEM_FILTERS_TABLEBAR + i)
        }

        for (i in 0 until nCount) {
            mProperties[ITEM_FILTERS_TITLE + i] = filters[i].mTitle
            mProperties[ITEM_FILTERS_FILTER + i] = filters[i].mValue
            mProperties[ITEM_FILTERS_TABLEBAR + i] = filters[i].mTableBar.toString()
        }

        saveConfig()
        return
    }

    fun loadCmds() : ArrayList<CustomListManager.CustomElement> {
        val cmds = ArrayList<CustomListManager.CustomElement>()

        var title: String
        var cmd: String
        var check: String
        var tableBar: Boolean
        for (i in 0 until CmdManager.MAX_CMD_COUNT) {
            title = (mProperties[ITEM_CMDS_TITLE + i] ?: "") as String
            if (title.isEmpty()) {
                break
            }
            cmd = (mProperties[ITEM_CMDS_CMD + i] ?: "null") as String
            check = (mProperties[ITEM_CMDS_TABLEBAR + i] ?: "false") as String
            tableBar = check.toBoolean()

            cmds.add(CustomListManager.CustomElement(title, cmd, tableBar))
        }

        return cmds
    }

    fun saveCmds(cmds : ArrayList<CustomListManager.CustomElement>) {
        loadConfig()

        var nCount = cmds.size
        if (nCount > CmdManager.MAX_CMD_COUNT) {
            nCount = CmdManager.MAX_CMD_COUNT
        }

        for (i in 0 until CmdManager.MAX_CMD_COUNT) {
            val title: String = (mProperties[ITEM_CMDS_TITLE + i] ?: "") as String
            if (title.isEmpty()) {
                break
            }
            mProperties.remove(ITEM_CMDS_TITLE + i)
            mProperties.remove(ITEM_CMDS_CMD + i)
            mProperties.remove(ITEM_CMDS_TABLEBAR + i)
        }

        for (i in 0 until nCount) {
            mProperties[ITEM_CMDS_TITLE + i] = cmds[i].mTitle
            mProperties[ITEM_CMDS_CMD + i] = cmds[i].mValue
            mProperties[ITEM_CMDS_TABLEBAR + i] = cmds[i].mTableBar.toString()
        }

        saveConfig()
        return
    }

    private fun manageVersion() {
        val isLoaded = loadConfig()

        if (isLoaded) {
            var confVer: String = (mProperties[ITEM_CONFIG_VERSION] ?: "") as String
            if (confVer.isEmpty()) {
                updateConfigFromV0ToV1()
                confVer = (mProperties[ITEM_CONFIG_VERSION] ?: "") as String
                println("manageVersion : $confVer applied")
            }

            if (confVer == "1") {
                updateConfigFromV1ToV2()
                confVer = (mProperties[ITEM_CONFIG_VERSION] ?: "") as String
                println("manageVersion : $confVer applied")
            }

//            if (confVer == "2") {
//                updateConfigFromV1ToV2()
//                confVer = (mProperties[ITEM_CONFIG_VERSION] ?: "") as String
//                println("manageVersion : $confVer applied")
//            }
        }
        else {
            mProperties[ITEM_CONFIG_VERSION] = "2"
//            mProperties[ITEM_CONFIG_VERSION] = "3"
        }

        saveConfig()
    }

    private fun updateConfigFromV0ToV1() {
        println("updateConfigFromV0ToV1 : change color manager properties ++")
        for (idx: Int in 0..22) {
            val item = mProperties["${ITEM_COLOR_MANAGER}$idx"] as String?
            if (item != null) {
                when (idx) {
                    2 -> {
                        mProperties["${ITEM_COLOR_MANAGER}${ColorManager.TableColorType.FULL_LOG_TABLE}_${ColorManager.TableColorIdx.LOG_BG.value}"] = item
                    }
                    3 -> {
                        mProperties["${ITEM_COLOR_MANAGER}${ColorManager.TableColorType.FILTER_LOG_TABLE}_${ColorManager.TableColorIdx.LOG_BG.value}"] = item
                    }
                    else -> {
                        mProperties["${ITEM_COLOR_MANAGER}${ColorManager.TableColorType.FULL_LOG_TABLE}_$idx"] = item
                        mProperties["${ITEM_COLOR_MANAGER}${ColorManager.TableColorType.FILTER_LOG_TABLE}_$idx"] = item
                    }
                }

                mProperties.remove("${ITEM_COLOR_MANAGER}$idx")
            }
        }
        mProperties[ITEM_CONFIG_VERSION] = "1"
        println("updateConfigFromV0ToV1 : --")
    }

    private fun updateConfigFromV1ToV2() {
        println("updateConfigFromV1ToV2 : change log level properties ++")
        val logLevel = mProperties[ITEM_LOG_LEVEL] as String?
        if (logLevel != null) {
            for (idx in FormatManager.TEXT_LEVEL.indices) {
                if (logLevel.startsWith(FormatManager.TEXT_LEVEL[idx])) {
                    mProperties[ITEM_LOG_LEVEL] = idx.toString()
                    break
                }
            }
        }

        mProperties[ITEM_CONFIG_VERSION] = "2"
        println("updateConfigFromV1ToV2 : --")
    }

    private fun updateConfigFromV2ToV3() {
        println("updateConfigFromV2ToV3 : change log level properties ++")
//        prop = mConfigManager.getItem(ConfigManager.ITEM_SHOW_TAG_STYLE)
//        prop = mConfigManager.getItem(ConfigManager.ITEM_SHOW_PID_STYLE)
//        prop = mConfigManager.getItem(ConfigManager.ITEM_SHOW_TID_STYLE)
//        mProperties.remove()

//        nCount = mShowTagCombo.itemCount
//        if (nCount > ConfigManager.COUNT_SHOW_TAG) {
//            nCount = ConfigManager.COUNT_SHOW_TAG
//        }
//        for (i in 0 until nCount) {
//            mConfigManager.setItem(ConfigManager.ITEM_SHOW_TAG + i, mShowTagCombo.getItemAt(i).toString())
//        }
//        for (i in nCount until ConfigManager.COUNT_SHOW_TAG) {
//            mConfigManager.removeConfigItem(ConfigManager.ITEM_SHOW_TAG + i)
//        }
//        check = mConfigManager.getItem(ConfigManager.ITEM_SHOW_TAG_CHECK)
//        if (!check.isNullOrEmpty()) {
//            mShowTagToggle.isSelected = check.toBoolean()
//        } else {
//            mShowTagToggle.isSelected = true
//        }
//        mShowTagCombo.setEnabledFilter(mShowTagToggle.isSelected)
//
//        check = mConfigManager.getItem(ConfigManager.ITEM_SHOW_PID_CHECK)
//        if (!check.isNullOrEmpty()) {
//            mShowPidToggle.isSelected = check.toBoolean()
//        } else {
//            mShowPidToggle.isSelected = true
//        }
//        mShowPidCombo.setEnabledFilter(mShowPidToggle.isSelected)
//
//        check = mConfigManager.getItem(ConfigManager.ITEM_SHOW_TID_CHECK)
//        mProperties[ITEM_SHOW_TAG_CHECK] = "true"
//        mProperties[ITEM_SHOW_PID_CHECK] = "true"
//        mProperties[ITEM_SHOW_TID_CHECK] = "true"
//

        mProperties[ITEM_CONFIG_VERSION] = "3"
        println("updateConfigFromV2ToV3 : --")
    }
}

