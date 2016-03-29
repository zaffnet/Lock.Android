/*
 * ChangePasswordFormView.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.android.lock.views;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.auth0.android.lock.R;
import com.auth0.android.lock.events.DatabaseChangePasswordEvent;
import com.auth0.android.lock.views.interfaces.LockWidget;

public class ChangePasswordFormView extends FormView implements TextView.OnEditorActionListener {

    private static final String TAG = ChangePasswordFormView.class.getSimpleName();
    private final LockWidget lockWidget;
    private ValidatedUsernameInputView usernameEmailInput;
    private View title;
    private View text;

    public ChangePasswordFormView(Context context) {
        super(context);
        lockWidget = null;
    }

    public ChangePasswordFormView(LockWidget lockWidget) {
        super(lockWidget.getContext());
        this.lockWidget = lockWidget;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.com_auth0_lock_changepwd_form_view, this);
        title = findViewById(R.id.com_auth0_lock_title);
        text = findViewById(R.id.com_auth0_lock_text);
        usernameEmailInput = (ValidatedUsernameInputView) findViewById(R.id.com_auth0_lock_input_username_email);
        usernameEmailInput.chooseDataType(lockWidget.getConfiguration());
        usernameEmailInput.setOnEditorActionListener(this);
    }

    @Override
    public Object getActionEvent() {
        return new DatabaseChangePasswordEvent(getUsernameOrEmail());
    }

    private String getUsernameOrEmail() {
        return usernameEmailInput.getText();
    }

    @Override
    public boolean validateForm() {
        return usernameEmailInput.validate(true);
    }

    @Override
    public Object submitForm() {
        if (validateForm()) {
            return getActionEvent();
        }
        return null;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            lockWidget.onFormSubmit();
        }
        return false;
    }

    /**
     * Notifies this forms and its child views that the keyboard state changed, so that
     * it can change the layout in order to fit all the fields.
     *
     * @param isOpen whether the keyboard is open or close.
     */
    public void onKeyboardStateChanged(boolean isOpen) {
        title.setVisibility(isOpen ? GONE : VISIBLE);
        text.setVisibility(isOpen ? GONE : VISIBLE);
    }
}
