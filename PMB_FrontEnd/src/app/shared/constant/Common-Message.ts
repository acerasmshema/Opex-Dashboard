import { Injectable } from '@angular/core';

@Injectable()
export class CommonMessage {

    static SUCCESS = {
        ANNOTATION_SAVED: "Annotation saved successfully.",
        ADD_SUCCESS: "added successfully.",
        UPDATE_SUCCESS: "updated successfully.",
        DELETE_SUCCESS: "deleted sucessfully",
        TARGET_CHANGED_SUCCESS: "Target days updated successfully"
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

    static ERROR_CODES = {
        1001: 'Invalid user credentials',
        1002: 'Maintenance day could not be added',
        1003: 'Target Days could not be updated',
        1004: 'Maintenance day remark could not be updated',
        1005: 'Maintenance Day(s) could not be deleted',
        1006: 'Annotation could not be saved',
        1007: 'Annotation could not be deleted',
        1008: 'User not exist',
        1009: 'Error during logout',
        1010: 'Record not found',
        1011: 'User is inactive. Please contact administrator',
        1012: 'Users are associated with this role so cannot be deactivated. Users must be assigned a new role'
    }
}