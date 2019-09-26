import { Inject, Injectable } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { Router } from '@angular/router';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';

@Injectable()
export class LocalStorageService {

     constructor(@Inject(LOCAL_STORAGE) private storage: StorageService, private router: Router) { }

     public storeUserDetails(userDetail: UserDetail): void {
      localStorage.s
          const userDetails = {
               userName: userDetail,
          };
          this.storage.set('userDetails', userDetails);
     }

     public fetchUserDetail(): UserDetail {
          if (this.storage.get('userDetails') == undefined) {
               return null;
          } else {
               const userDetails = this.storage.get('userDetails');
               return userDetails['userName'];
          }
     }

     public removeUserDetail(): boolean {
          let flag = false;
          try {
               this.storage.remove('userDetails');
               flag = true;
          } catch (error) {
               flag = false;
          }
          return flag;
     }

}