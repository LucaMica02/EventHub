export interface Event {
  id: number;
  creator: number;
  title: string;
  description: string;
  freeSpots: number;
  ranking: number;
  startBooking: string;
  endBooking: string;
  startEvent: string;
  endEvent: string;
  state: string;
  price: number;
  url: string;
  street: string;
  zipcode: string;
  city: string;
  nation: string;
  online: boolean;
}

export interface User {
  id: number;
  name: string;
  lastname: string;
  username: string;
  email: string;
  password: string;
  street: string;
  zipcode: string;
  city: string;
  nation: string;
  isCreator: boolean;
  taxId: String;
  imageUrl: String;
}

export interface FeedbackType {
  event: number;
  stars: number;
  description: string;
  user: number;
  dateTime: string;
}

export interface CreatorType {
  id: number;
  name: string;
  lastname: string;
  username: string;
  email: string;
  feedbacksCount: number;
  rating: number;
  eventsCount: number;
}
