import { Injectable } from '@angular/core';

@Injectable()
export class CommonMessage {

    static SUCCESS = {
        ANNOTATION_SAVED: "Annotation saved successfully.",
        ADD_SUCCESS: "Added successfully.",
        UPDATE_SUCCESS: "Updated successfully.",
        DELETE_SUCCESS: "Deleted sucessfully",
        TARGET_CHANGED_SUCCESS: "Target days changed successfully"
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
        DATE_ERROR: "Please select date range",
        REMARKS_ERROR: "Please enter Remarks",
        TARGET_DAYS_ERROR: "Please enter target days",
        MILLS_SELECT: "Please select atleast 2 mills",
        SERVER_ERROR: "Server Error",
    };

    static ERROR_CODES ={
        1001 : 'Invalid user credentials',
        1002 : 'Maintenance Day Could not be Added',
        1003 : 'Target Days Could not be Updated',
        1004 : 'Maintenance Day Remark Could not be Updated',
        1005 : 'Maintenance Day(s) Could not be Deleted',
        1006 : 'Annotation Could not be Added/Saved',
        1007 : 'Annotation Could not be Deleted',
        1008 : 'User not exist',
        1009 : 'Error during logout',
        1010 : 'Record Not Found'
    }
}