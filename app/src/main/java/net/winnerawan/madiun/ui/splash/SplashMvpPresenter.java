package net.winnerawan.madiun.ui.splash;

import net.winnerawan.madiun.ui.base.MvpPresenter;

/**
 * Copyright 2017 Winnerawan T
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 * Written by Winnerawan T <winnerawan@gmail.com>, June 2017
 */

public interface SplashMvpPresenter<V extends SplashView> extends MvpPresenter<V> {

    void app();
}
