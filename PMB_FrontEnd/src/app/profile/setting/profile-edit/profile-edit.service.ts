import { Injectable } from "@angular/core";
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { CommonService } from 'src/app/shared/service/common/common.service';

@Injectable()
export class ProfileEditService {

    constructor(private formBuilder: FormBuilder,
        private commonService: CommonService,
        private statusService: StatusService) { }

    createUserDetailForm(): FormGroup {
        const userDetail = this.statusService.common.userDetail;

        let userDetailForm = this.formBuilder.group({
            firstName: new FormControl(userDetail.firstName),
            lastName: new FormControl(userDetail.lastName),
            email: new FormControl(userDetail.email, [Validators.required, Validators.email]),
            phone: new FormControl(userDetail.phone),
            address: new FormControl(userDetail.address),
            country: new FormControl(userDetail.country),
            countryList: this.formBuilder.array([]),
            department: new FormControl(userDetail.department),
            departmentList: this.formBuilder.array([])
        });

        this.commonService.getAllDepartment();
        this.commonService.getAllCountry(userDetailForm);

        return userDetailForm;
    }
}