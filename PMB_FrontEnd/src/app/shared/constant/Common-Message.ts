import { Injectable } from '@angular/core';

@Injectable()
export class CommonMessage {

    static SUCCESS = {
        ANNOTATION_SAVED: "Annotation saved successfully.",
        ADD_SUCCESS: "Added Successfully.",
        DELETE_SUCCESS: "Deleted sucessfully",
        TARGET_CHANGED_SUCCESS: "Target days changed successfully",
    };

    static ERROR = {
        USERNAME_VALIDATION: 'Username is required',
        PASSWORD_VALIDATION: 'Password is required',
        INVALID_USER: 'Invalid user credentials',
        PROCESS_LINE_VALIDATION: 'Please select process lines.',
        ADD_DESCRIPTION_VALIDATION: "Please add description.",
        ADD_TARGET_DAYS: "Please enter target days.",
        TARGET_DAYS_GREATER_THAN_ZERO: "Please enter target days value greater than 0.",
        ANNOTATION_ERROR: "Annotation could not be saved.",
        DATE_ERROR: "Please select Date.",
        REMARKS_ERROR: "Please enter Remarks.",
    };
}