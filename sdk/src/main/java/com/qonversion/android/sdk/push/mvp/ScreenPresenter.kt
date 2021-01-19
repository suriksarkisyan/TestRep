package com.qonversion.android.sdk.push.mvp

import android.net.Uri
import com.qonversion.android.sdk.QonversionRepository
import com.qonversion.android.sdk.logger.ConsoleLogger
import com.qonversion.android.sdk.push.QActionType
import javax.inject.Inject

class ScreenPresenter @Inject constructor(
    private val repository: QonversionRepository,
    private val view: ScreenContract.View
) : ScreenContract.Presenter {

    private val logger = ConsoleLogger()

    override fun shouldOverrideUrlLoading(url: String?): Boolean {
        logger.debug("shouldOverrideUrlLoading() -> url:$url")

        if (url == null) {
            return true
        }

        val uri = Uri.parse(url)
        if (!uri.shouldOverrideUrlLoading()) {
            return true
        }

        when (uri.getActionType()) {
            QActionType.Url -> {
                val link = uri.getData()
                if (link != null) {
                    view.openLink(link)
                }
            }
            QActionType.DeepLink -> {
                val deepLink = uri.getData()
                if (deepLink != null) {
                    view.openLink(deepLink)
                }
            }
            QActionType.Close -> {
                view.close()
            }
            QActionType.Navigate -> {
                val screenId = uri.getData()
                if (screenId != null) {
                    getHtmlPageForScreen(screenId)
                }
            }
            QActionType.Purchase -> {
                val productId = uri.getData()
                if (productId != null) {
                    view.purchase(productId)
                }
            }
            QActionType.Restore -> {
                view.restore()
            }
            else -> return true
        }
        return true
    }

    override fun confirmScreenView(screenId: String) {
        repository.views(screenId)
    }

    private fun Uri.getActionType(): QActionType {
        val actionType = getQueryParameter(ACTION)
        logger.debug("getActionType() $QActionType.fromType(actionType)")
        return QActionType.fromType(actionType)
    }

    private fun Uri.getData() = getQueryParameter(DATA)

    private fun Uri.shouldOverrideUrlLoading() = isAutomationsHost() && isQScheme()

    private fun Uri.isQScheme(): Boolean {
        val uriScheme = scheme
        if (uriScheme != null) {
            val pattern = REGEX.toRegex()
            return pattern.matches(uriScheme)
        }
        return false
    }

    private fun Uri.isAutomationsHost() = host.equals(HOST)

    private fun getHtmlPageForScreen(screenId: String) {
        repository.screens(screenId,
            { screen ->
                view.openScreen(screenId, screen.htmlPage)
            },
            {
                view.onError(it)
            }
        )
    }

    companion object {
        private const val ACTION = "action"
        private const val DATA = "data"
        private const val SCHEMA = "qon-"
        private const val HOST = "automation"
        private const val REGEX = "$SCHEMA.+"
    }
}