import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ProfileEditService } from './profile-edit.service';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss'],
  providers: [ProfileEditService]
})
export class ProfileEditComponent implements OnInit {

  userDetailForm: FormGroup;

  constructor(private profileEditService: ProfileEditService) { }

  ngOnInit() {
    this.userDetailForm = this.profileEditService.createUserDetailForm();
    console.log(this.userDetailForm)
  }

}
