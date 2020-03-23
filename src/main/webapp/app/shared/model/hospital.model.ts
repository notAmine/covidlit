import { IUser } from 'app/core/user/user.model';
import { IBed } from 'app/shared/model/bed.model';

export interface IHospital {
  id?: number;
  name?: string;
  streetAddress?: string;
  city?: string;
  headOfSearvice?: IUser;
  beds?: IBed[];
}

export class Hospital implements IHospital {
  constructor(
    public id?: number,
    public name?: string,
    public streetAddress?: string,
    public city?: string,
    public headOfSearvice?: IUser,
    public beds?: IBed[]
  ) {}
}
