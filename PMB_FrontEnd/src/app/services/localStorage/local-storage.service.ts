import { Inject, Injectable } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';
import { Router } from '@angular/router';

@Injectable()
export class LocalStorageService {
     anotherTodolist = [];
     constructor(@Inject(LOCAL_STORAGE) private storage: StorageService,private router: Router) { }

     public storeUserDetails(userName: string,userRole: string,loginId: string): void {
          const userDetails = {
               userName: userName,
               userRole: userRole,
               loginId:loginId
          };
          this.storage.set('userDetails', userDetails);
     }
     

     public fetchUserRole(): string {
          const userDetails = this.storage.get('userDetails');
          return userDetails['userRole'];
     }


     public fetchUserName(): string {
          if(this.storage.get('userDetails')==undefined){
               return null;
          }else{
               const userDetails = this.storage.get('userDetails');
               return userDetails['userName'];
          }
     }

     public fetchloginId(): string {
          const userDetails = this.storage.get('userDetails');
          return userDetails['loginId'];
     }


     public emptyLocalStorage(): void {
          this.storage.remove('userDetails');
     }
}