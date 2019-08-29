import { Injectable } from '@angular/core';
import { AppConstants } from 'src/app/app.constant';
import { ApiCallService } from '../../shared/service/APIService/ApiCall.service';

@Injectable()
export class DialogService {

    saveAnnotation = AppConstants.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = AppConstants.apiURLs.FIND_ANNOTATION_URL;
    deleteAnnotation = AppConstants.apiURLs.DEL_ANNOTATION_URL;


    constructor(private apiCallService: ApiCallService) { }

    public createAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.saveAnnotation, data);
    }

    public fetchAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.findAnnotation, data);
    }


    public deleteAnnotationLists(data: any) {
        return this.apiCallService.callAPIwithData(this.deleteAnnotation, data);
    }

}