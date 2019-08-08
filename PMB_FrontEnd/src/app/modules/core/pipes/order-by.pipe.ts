import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'orderBy'
})
export class OrderByPipe implements PipeTransform {

  transform(array: any, field: string): any[] {
    if (!Array.isArray(array)) {
      return;
    }
    
    if(field[0] == "-") {
      array.sort((a: any, b: any) => a[field] > b[field] ? 1 : -1);
    } else {
      array.sort((a: any, b: any) => a[field] < b[field] ? -1 : 1);
    }
    return array;
  }

}
