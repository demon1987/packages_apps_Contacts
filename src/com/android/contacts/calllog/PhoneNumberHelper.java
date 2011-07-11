/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.contacts.calllog;

import com.android.contacts.R;
import com.android.internal.telephony.CallerInfo;

import android.content.res.Resources;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

/**
 * Helper for formatting and managing phone numbers.
 */
public class PhoneNumberHelper {
    private final Resources mResources;
    private final String mVoicemailNumber;

    public PhoneNumberHelper(Resources resources, String voicemailNumber) {
        mResources = resources;
        mVoicemailNumber = voicemailNumber;
    }

    /** Returns true if it is possible to place a call to the given number. */
    public boolean canPlaceCallsTo(CharSequence number) {
        return !(TextUtils.isEmpty(number)
                || number.equals(CallerInfo.UNKNOWN_NUMBER)
                || number.equals(CallerInfo.PRIVATE_NUMBER)
                || number.equals(CallerInfo.PAYPHONE_NUMBER));
    }

    /**
     * Returns the string to display for the given phone number.
     *
     * @param number the number to display
     * @param formattedNumber the formatted number if available, may be null
     */
    public CharSequence getDisplayNumber(CharSequence number, CharSequence formattedNumber) {
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        if (number.equals(CallerInfo.UNKNOWN_NUMBER)) {
            return mResources.getString(R.string.unknown);
        }
        if (number.equals(CallerInfo.PRIVATE_NUMBER)) {
            return mResources.getString(R.string.private_num);
        }
        if (number.equals(CallerInfo.PAYPHONE_NUMBER)) {
            return mResources.getString(R.string.payphone);
        }
        if (PhoneNumberUtils.extractNetworkPortion(number.toString()).equals(mVoicemailNumber)) {
            return mResources.getString(R.string.voicemail);
        }
        if (TextUtils.isEmpty(formattedNumber)) {
            return number;
        } else {
            return formattedNumber;
        }
    }

    /** Returns a URI that can be used to place a call to this number. */
    public Uri getCallUri(String number) {
        if (PhoneNumberUtils.isUriNumber(number)) {
             return Uri.fromParts("sip", number, null);
         } else {
             return Uri.fromParts("tel", number, null);
         }
    }
}
